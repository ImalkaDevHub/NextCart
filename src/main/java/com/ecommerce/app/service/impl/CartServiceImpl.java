package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.CartItemDto;
import com.ecommerce.app.dto.CartSummaryDto;
import com.ecommerce.app.entity.Cart;
import com.ecommerce.app.entity.CartItem;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.exception.InsufficientStockException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.CartItemRepository;
import com.ecommerce.app.repository.CartRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Value("${app.tax.rate:0.08}")
    private BigDecimal taxRate;

    @Value("${app.delivery.charge:15.00}")
    private BigDecimal deliveryCharge;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));
    }

    @Override
    @Transactional(readOnly = true)
    public CartSummaryDto getCartSummary(User user) {
        Cart cart = getOrCreateCart(user);
        return buildCartSummary(cart);
    }

    @Override
    @Transactional
    public CartSummaryDto addToCart(User user, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Only " + product.getStock() + " units available in stock.");
        }

        Cart cart = getOrCreateCart(user);
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                throw new InsufficientStockException("Cannot add more. Stock limit of " + product.getStock() + " reached.");
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        log.info("Added product ID {} (qty: {}) to cart for user {}", productId, quantity, user.getEmail());
        return buildCartSummary(cart);
    }

    @Override
    @Transactional
    public CartSummaryDto updateCartItemQuantity(User user, Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItemId));

        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Unauthorized cart modification attempt.");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            item.getCart().getItems().remove(item);
        } else {
            if (item.getProduct().getStock() < quantity) {
                throw new InsufficientStockException("Requested quantity exceeds available stock (" + item.getProduct().getStock() + ").");
            }
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return buildCartSummary(item.getCart());
    }

    @Override
    @Transactional
    public CartSummaryDto removeFromCart(User user, Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItemId));

        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Unauthorized cart modification attempt.");
        }

        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        log.info("Removed cart item ID {} for user {}", cartItemId, user.getEmail());
        return buildCartSummary(cart);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteByCart(cart);
        cart.getItems().clear();
        log.info("Cleared cart for user {}", user.getEmail());
    }

    private CartSummaryDto buildCartSummary(Cart cart) {
        List<CartItemDto> itemDtos = cart.getItems() != null ? cart.getItems().stream()
                .map(item -> CartItemDto.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getProductName())
                        .productImage(item.getProduct().getImage())
                        .price(item.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .itemTotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .availableStock(item.getProduct().getStock())
                        .build())
                .collect(Collectors.toList()) : new ArrayList<>();

        BigDecimal subtotal = itemDtos.stream()
                .map(CartItemDto::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tax = subtotal.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal shipping = subtotal.compareTo(BigDecimal.ZERO) > 0 ? deliveryCharge : BigDecimal.ZERO;
        BigDecimal grandTotal = subtotal.add(tax).add(shipping).setScale(2, RoundingMode.HALF_UP);

        int totalItems = itemDtos.stream().mapToInt(CartItemDto::getQuantity).sum();

        return CartSummaryDto.builder()
                .items(itemDtos)
                .subtotal(subtotal)
                .taxAmount(tax)
                .shippingFee(shipping)
                .grandTotal(grandTotal)
                .totalItems(totalItems)
                .build();
    }
}
