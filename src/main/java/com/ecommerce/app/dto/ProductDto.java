package com.ecommerce.app.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String description;

    private String brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private String image;

    private MultipartFile imageFile;

    private String specifications;

    private Boolean isFeatured = false;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private String categoryName;

    private Double averageRating;

    private Integer reviewCount;

    public ProductDto() {}

    public ProductDto(Long id, String productName, String description, String brand, BigDecimal price, Integer stock, String image, MultipartFile imageFile, String specifications, Boolean isFeatured, Long categoryId, String categoryName, Double averageRating, Integer reviewCount) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.image = image;
        this.imageFile = imageFile;
        this.specifications = specifications;
        this.isFeatured = isFeatured != null ? isFeatured : false;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.averageRating = averageRating != null ? averageRating : 5.0;
        this.reviewCount = reviewCount != null ? reviewCount : 0;
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
    public MultipartFile getImageFile() { return imageFile; }
    public void setImageFile(MultipartFile imageFile) { this.imageFile = imageFile; }
    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }
    public Boolean getIsFeatured() { return isFeatured; }
    public void setIsFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String productName;
        private String description;
        private String brand;
        private BigDecimal price;
        private Integer stock;
        private String image;
        private MultipartFile imageFile;
        private String specifications;
        private Boolean isFeatured = false;
        private Long categoryId;
        private String categoryName;
        private Double averageRating = 5.0;
        private Integer reviewCount = 0;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder productName(String productName) { this.productName = productName; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder stock(Integer stock) { this.stock = stock; return this; }
        public Builder image(String image) { this.image = image; return this; }
        public Builder imageFile(MultipartFile imageFile) { this.imageFile = imageFile; return this; }
        public Builder specifications(String specifications) { this.specifications = specifications; return this; }
        public Builder isFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; return this; }
        public Builder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public Builder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public Builder averageRating(Double averageRating) { this.averageRating = averageRating; return this; }
        public Builder reviewCount(Integer reviewCount) { this.reviewCount = reviewCount; return this; }

        public ProductDto build() {
            return new ProductDto(id, productName, description, brand, price, stock, image, imageFile, specifications, isFeatured, categoryId, categoryName, averageRating, reviewCount);
        }
    }
}
