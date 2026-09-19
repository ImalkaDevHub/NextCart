package com.ecommerce.app.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime orderDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(length = 20)
    private String recipientPhone;

    @Column(length = 100)
    private String recipientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(Long id, String orderNumber, User user, LocalDateTime orderDate, BigDecimal subtotal, BigDecimal taxAmount, BigDecimal shippingFee, BigDecimal totalAmount, String shippingAddress, String recipientPhone, String recipientName, PaymentMethod paymentMethod, PaymentStatus paymentStatus, OrderStatus orderStatus, List<OrderItem> orderItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.user = user;
        this.orderDate = orderDate;
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.recipientPhone = recipientPhone;
        this.recipientName = recipientName;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    }

    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
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
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String orderNumber;
        private User user;
        private LocalDateTime orderDate;
        private BigDecimal subtotal;
        private BigDecimal taxAmount;
        private BigDecimal shippingFee;
        private BigDecimal totalAmount;
        private String shippingAddress;
        private String recipientPhone;
        private String recipientName;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus;
        private OrderStatus orderStatus;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder orderNumber(String orderNumber) { this.orderNumber = orderNumber; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder orderDate(LocalDateTime orderDate) { this.orderDate = orderDate; return this; }
        public Builder subtotal(BigDecimal subtotal) { this.subtotal = subtotal; return this; }
        public Builder taxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; return this; }
        public Builder shippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; return this; }
        public Builder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public Builder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public Builder recipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; return this; }
        public Builder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public Builder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public Builder paymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public Builder orderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; return this; }
        public Builder orderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; return this; }

        public Order build() {
            return new Order(id, orderNumber, user, orderDate, subtotal, taxAmount, shippingFee, totalAmount, shippingAddress, recipientPhone, recipientName, paymentMethod, paymentStatus, orderStatus, orderItems);
        }
    }
}
