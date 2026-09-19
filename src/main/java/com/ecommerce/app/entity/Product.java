package com.ecommerce.app.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String brand;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    private Boolean isFeatured = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    private LocalDateTime createdAt;

    public Product() {}

    public Product(Long id, String productName, String description, String brand, BigDecimal price, Integer stock, String image, String specifications, Boolean isFeatured, Category category, List<Review> reviews, LocalDateTime createdAt) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.specifications = specifications;
        this.isFeatured = isFeatured != null ? isFeatured : false;
        this.category = category;
        this.reviews = reviews != null ? reviews : new ArrayList<>();
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    public Boolean getIsFeatured() { return isFeatured; }
    public void setIsFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String productName;
        private String description;
        private String brand;
        private BigDecimal price;
        private Integer stock;
        private String image;
        private String specifications;
        private Boolean isFeatured = false;
        private Category category;
        private List<Review> reviews = new ArrayList<>();
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder productName(String productName) { this.productName = productName; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder stock(Integer stock) { this.stock = stock; return this; }
        public Builder image(String image) { this.image = image; return this; }
        public Builder specifications(String specifications) { this.specifications = specifications; return this; }
        public Builder isFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; return this; }
        public Builder category(Category category) { this.category = category; return this; }
        public Builder reviews(List<Review> reviews) { this.reviews = reviews; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Product build() {
            return new Product(id, productName, description, brand, price, stock, image, specifications, isFeatured, category, reviews, createdAt);
        }
    }
}
