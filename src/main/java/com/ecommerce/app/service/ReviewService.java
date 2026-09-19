package com.ecommerce.app.service;

import com.ecommerce.app.dto.ReviewDto;
import com.ecommerce.app.entity.User;
import java.util.List;

public interface ReviewService {
    ReviewDto addReview(User user, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByProduct(Long productId);
}
