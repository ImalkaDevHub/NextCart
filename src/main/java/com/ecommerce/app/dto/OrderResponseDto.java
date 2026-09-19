package com.ecommerce.app.dto;

import com.ecommerce.app.entity.OrderStatus;
import com.ecommerce.app.entity.PaymentMethod;
import com.ecommerce.app.entity.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {

    private Long id;
    private String orderNumber;
    private Long userId;
    private String userFullName;
    private String userEmail;
    private LocalDateTime orderDate;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private String recipientName;
    private String recipientPhone;
    private String shippingAddress;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private List<CartItemDto> items;

    public OrderResponseDto() {}

    public OrderResponseDto(Long id, String orderNumber, Long userId, String userFullName, String userEmail, LocalDateTime orderDate, BigDecimal subtotal, BigDecimal taxAmount, BigDecimal shippingFee, BigDecimal totalAmount, String recipientName, String recipientPhone, String shippingAddress, PaymentMethod paymentMethod, PaymentStatus paymentStatus, OrderStatus orderStatus, List<CartItemDto> items) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.orderDate = orderDate;
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getShippingFee() { return shippingFee; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String orderNumber;
        private Long userId;
        private String userFullName;
        private String userEmail;
        private LocalDateTime orderDate;
        private BigDecimal subtotal;
        private BigDecimal taxAmount;
        private BigDecimal shippingFee;
        private BigDecimal totalAmount;
        private String recipientName;
        private String recipientPhone;
        private String shippingAddress;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus;
        private OrderStatus orderStatus;
        private List<CartItemDto> items;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder orderNumber(String orderNumber) { this.orderNumber = orderNumber; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder userFullName(String userFullName) { this.userFullName = userFullName; return this; }
        public Builder userEmail(String userEmail) { this.userEmail = userEmail; return this; }
        public Builder orderDate(LocalDateTime orderDate) { this.orderDate = orderDate; return this; }
        public Builder subtotal(BigDecimal subtotal) { this.subtotal = subtotal; return this; }
        public Builder taxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; return this; }
        public Builder shippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; return this; }
        public Builder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public Builder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public Builder recipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; return this; }
        public Builder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public Builder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public Builder paymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public Builder orderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; return this; }
        public Builder items(List<CartItemDto> items) { this.items = items; return this; }

        public OrderResponseDto build() {
            return new OrderResponseDto(id, orderNumber, userId, userFullName, userEmail, orderDate, subtotal, taxAmount, shippingFee, totalAmount, recipientName, recipientPhone, shippingAddress, paymentMethod, paymentStatus, orderStatus, items);
        }
    }
}
