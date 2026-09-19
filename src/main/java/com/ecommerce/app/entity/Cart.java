package com.ecommerce.app.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private LocalDateTime updatedAt;

    public Cart() {}

    public Cart(Long id, User user, List<CartItem> items, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.items = items != null ? items : new ArrayList<>();
        this.updatedAt = updatedAt;
    }

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private User user;
        private List<CartItem> items = new ArrayList<>();
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder items(List<CartItem> items) { this.items = items; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Cart build() {
            return new Cart(id, user, items, updatedAt);
        }
    }
}
