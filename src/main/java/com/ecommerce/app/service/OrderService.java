package com.ecommerce.app.service;

import com.ecommerce.app.dto.CheckoutDto;
import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.entity.OrderStatus;
import com.ecommerce.app.entity.User;

import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(User user, CheckoutDto checkoutDto);
    List<OrderResponseDto> getUserOrders(User user);
    OrderResponseDto getOrderByIdAndUser(Long orderId, User user);
    OrderResponseDto getOrderByOrderNumber(String orderNumber);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);
    OrderResponseDto cancelOrder(Long orderId, User user);
}
