package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.ReviewDto;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.Review;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.ReviewRepository;
import com.ecommerce.app.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ReviewDto addReview(User user, ReviewDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + dto.getProductId()));

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        Review saved = reviewRepository.save(review);
        log.info("Saved review for product ID {} by user {}", dto.getProductId(), user.getEmail());
        return mapToDto(saved);
    }

    @Override
    public List<ReviewDto> getReviewsByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        return reviewRepository.findByProductOrderByCreatedAtDesc(product).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReviewDto mapToDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .userName(review.getUser().getFullName())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
