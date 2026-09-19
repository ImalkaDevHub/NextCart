package com.ecommerce.app.dto;

import java.math.BigDecimal;

public class ProductSearchFilterDto {

    private String keyword;
    private Long categoryId;
    private String brand;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sortBy;
    private Integer page = 0;
    private Integer size = 9;

    public ProductSearchFilterDto() {}

    public ProductSearchFilterDto(String keyword, Long categoryId, String brand, BigDecimal minPrice, BigDecimal maxPrice, String sortBy, Integer page, Integer size) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.brand = brand;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortBy = sortBy;
        this.page = page != null ? page : 0;
        this.size = size != null ? size : 9;
    }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String keyword;
        private Long categoryId;
        private String brand;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private String sortBy;
        private Integer page = 0;
        private Integer size = 9;

        public Builder keyword(String keyword) { this.keyword = keyword; return this; }
        public Builder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder minPrice(BigDecimal minPrice) { this.minPrice = minPrice; return this; }
        public Builder maxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; return this; }
        public Builder sortBy(String sortBy) { this.sortBy = sortBy; return this; }
        public Builder page(Integer page) { this.page = page; return this; }
        public Builder size(Integer size) { this.size = size; return this; }

        public ProductSearchFilterDto build() {
            return new ProductSearchFilterDto(keyword, categoryId, brand, minPrice, maxPrice, sortBy, page, size);
        }
    }
}
