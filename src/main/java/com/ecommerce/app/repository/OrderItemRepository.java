package com.ecommerce.app.repository;

import com.ecommerce.app.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    @Query("SELECT oi.product.id, SUM(oi.quantity) as totalSold FROM OrderItem oi GROUP BY oi.product.id ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProductIds();
}
