package com.ecommerce.app.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public Category() {}

    public Category(Long id, String categoryName, String description, String imageUrl, List<Product> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.products = products != null ? products : new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String categoryName;
        private String description;
        private String imageUrl;
        private List<Product> products = new ArrayList<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder products(List<Product> products) { this.products = products; return this; }

        public Category build() {
            return new Category(id, categoryName, description, imageUrl, products);
        }
    }
}
