package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.entity.Wishlist;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.WishlistRepository;
import com.ecommerce.app.service.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private static final Logger log = LoggerFactory.getLogger(WishlistServiceImpl.class);

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getUserWishlist(User user) {
        return wishlistRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(w -> mapProductToDto(w.getProduct()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addToWishlist(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        if (!wishlistRepository.existsByUserAndProduct(user, product)) {
            Wishlist wishlist = Wishlist.builder()
                    .user(user)
                    .product(product)
                    .build();
            wishlistRepository.save(wishlist);
            log.info("Added product ID {} to wishlist for user {}", productId, user.getEmail());
        }
    }

    @Override
    @Transactional
    public void removeFromWishlist(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        wishlistRepository.deleteByUserAndProduct(user, product);
        log.info("Removed product ID {} from wishlist for user {}", productId, user.getEmail());
    }

    @Override
    public boolean isInWishlist(User user, Long productId) {
        if (user == null) return false;
        Product product = productRepository.findById(productId).orElse(null);
        return product != null && wishlistRepository.existsByUserAndProduct(user, product);
    }

    private ProductDto mapProductToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stock(product.getStock())
                .image(product.getImage())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null)
                .build();
    }
}
