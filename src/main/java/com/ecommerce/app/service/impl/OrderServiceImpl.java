package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.CartItemDto;
import com.ecommerce.app.dto.CartSummaryDto;
import com.ecommerce.app.dto.CheckoutDto;
import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.entity.*;
import com.ecommerce.app.exception.InsufficientStockException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.OrderRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public OrderResponseDto placeOrder(User user, CheckoutDto checkoutDto) {
        CartSummaryDto cartSummary = cartService.getCartSummary(user);

        if (cartSummary.getItems() == null || cartSummary.getItems().isEmpty()) {
            throw new IllegalStateException("Your cart is empty. Add products before checking out.");
        }

        // Validate stock availability for all items first
        for (CartItemDto item : cartSummary.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + item.getProductId()));
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product '" + product.getProductName() + "'. Available: " + product.getStock());
            }
        }

        // Simulated Payment Check
        PaymentStatus paymentStatus = PaymentStatus.PAID;
        if (checkoutDto.getPaymentMethod() == PaymentMethod.COD) {
            paymentStatus = PaymentStatus.PENDING;
        } else if (Boolean.TRUE.equals(checkoutDto.getSimulateFailure())) {
            paymentStatus = PaymentStatus.FAILED;
        }

        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .user(user)
                .subtotal(cartSummary.getSubtotal())
                .taxAmount(cartSummary.getTaxAmount())
                .shippingFee(cartSummary.getShippingFee())
                .totalAmount(cartSummary.getGrandTotal())
                .shippingAddress(checkoutDto.getShippingAddress())
                .recipientName(checkoutDto.getRecipientName())
                .recipientPhone(checkoutDto.getRecipientPhone())
                .paymentMethod(checkoutDto.getPaymentMethod())
                .paymentStatus(paymentStatus)
                .orderStatus(paymentStatus == PaymentStatus.FAILED ? OrderStatus.CANCELLED : OrderStatus.PENDING)
                .orderItems(new ArrayList<>())
                .build();

        if (paymentStatus != PaymentStatus.FAILED) {
            // Deduct stock and populate order items
            for (CartItemDto item : cartSummary.getItems()) {
                Product product = productRepository.findById(item.getProductId()).get();
                product.setStock(product.getStock() - item.getQuantity());
                productRepository.save(product);

                OrderItem orderItem = OrderItem.builder()
                        .order(order)
                        .product(product)
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build();
                order.getOrderItems().add(orderItem);
            }

            // Clear the cart
            cartService.clearCart(user);
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order {} created for user {} with status {}", orderNumber, user.getEmail(), paymentStatus);
        return mapToDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderByIdAndUser(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("Unauthorized access to order.");
        }

        return mapToDto(order);
    }

    @Override
    public OrderResponseDto getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with number: " + orderNumber));
        return mapToDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .sorted((a, b) -> b.getOrderDate().compareTo(a.getOrderDate()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        order.setOrderStatus(status);
        if (status == OrderStatus.DELIVERED && order.getPaymentMethod() == PaymentMethod.COD) {
            order.setPaymentStatus(PaymentStatus.PAID);
        }

        Order updated = orderRepository.save(order);
        log.info("Updated order ID {} status to {}", orderId, status);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new IllegalArgumentException("Unauthorized action.");
        }

        if (order.getOrderStatus() == OrderStatus.DELIVERED || order.getOrderStatus() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel an order that is already " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        // Restore stock
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        Order updated = orderRepository.save(order);
        log.info("Cancelled order ID {}", orderId);
        return mapToDto(updated);
    }

    private OrderResponseDto mapToDto(Order order) {
        List<CartItemDto> items = order.getOrderItems() != null ? order.getOrderItems().stream()
                .map(item -> CartItemDto.builder()
                        .id(item.getId())
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getProductName())
                        .productImage(item.getProduct().getImage())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .itemTotal(item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .collect(Collectors.toList()) : new ArrayList<>();

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .userFullName(order.getUser().getFullName())
                .userEmail(order.getUser().getEmail())
                .orderDate(order.getOrderDate())
                .subtotal(order.getSubtotal())
                .taxAmount(order.getTaxAmount())
                .shippingFee(order.getShippingFee())
                .totalAmount(order.getTotalAmount())
                .recipientName(order.getRecipientName())
                .recipientPhone(order.getRecipientPhone())
                .shippingAddress(order.getShippingAddress())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .orderStatus(order.getOrderStatus())
                .items(items)
                .build();
    }
}
