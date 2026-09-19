package com.ecommerce.app.service;

import com.ecommerce.app.dto.CartSummaryDto;
import com.ecommerce.app.entity.User;

public interface CartService {
    CartSummaryDto getCartSummary(User user);
    CartSummaryDto addToCart(User user, Long productId, Integer quantity);
    CartSummaryDto updateCartItemQuantity(User user, Long cartItemId, Integer quantity);
    CartSummaryDto removeFromCart(User user, Long cartItemId);
    void clearCart(User user);
}
