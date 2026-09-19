package com.ecommerce.app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReviewDto {

    private Long id;
    private Long productId;
    private String userName;

    @NotNull(message = "Please provide a rating")
    @Min(value = 1, message = "Minimum rating is 1")
    @Max(value = 5, message = "Maximum rating is 5")
    private Integer rating;

    private String comment;
    private LocalDateTime createdAt;

    public ReviewDto() {}

    public ReviewDto(Long id, Long productId, String userName, Integer rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long productId;
        private String userName;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder productId(Long productId) { this.productId = productId; return this; }
        public Builder userName(String userName) { this.userName = userName; return this; }
        public Builder rating(Integer rating) { this.rating = rating; return this; }
        public Builder comment(String comment) { this.comment = comment; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public ReviewDto build() {
            return new ReviewDto(id, productId, userName, rating, comment, createdAt);
        }
    }
}
