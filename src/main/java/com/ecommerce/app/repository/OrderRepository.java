package com.ecommerce.app.repository;

import com.ecommerce.app.entity.Order;
import com.ecommerce.app.entity.OrderStatus;
import com.ecommerce.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findTop5ByOrderByOrderDateDesc();
    List<Order> findByOrderStatus(OrderStatus status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.paymentStatus = com.ecommerce.app.entity.PaymentStatus.PAID")
    BigDecimal calculateTotalRevenue();
}
