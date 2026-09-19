package com.ecommerce.app.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {

    private Long id;

    @NotBlank(message = "Category name is required")
    private String categoryName;

    private String description;

    private String imageUrl;

    private Integer productCount;

    public CategoryDto() {}

    public CategoryDto(Long id, String categoryName, String description, String imageUrl, Integer productCount) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.productCount = productCount != null ? productCount : 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Integer getProductCount() { return productCount; }
    public void setProductCount(Integer productCount) { this.productCount = productCount; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String categoryName;
        private String description;
        private String imageUrl;
        private Integer productCount = 0;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder productCount(Integer productCount) { this.productCount = productCount; return this; }

        public CategoryDto build() {
            return new CategoryDto(id, categoryName, description, imageUrl, productCount);
        }
    }
}
