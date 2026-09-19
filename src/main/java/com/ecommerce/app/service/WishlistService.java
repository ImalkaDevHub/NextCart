package com.ecommerce.app.service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.entity.User;
import java.util.List;

public interface WishlistService {
    List<ProductDto> getUserWishlist(User user);
    void addToWishlist(User user, Long productId);
    void removeFromWishlist(User user, Long productId);
    boolean isInWishlist(User user, Long productId);
}
