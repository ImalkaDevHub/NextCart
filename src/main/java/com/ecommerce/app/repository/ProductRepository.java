package com.ecommerce.app.repository;

import com.ecommerce.app.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByIsFeaturedTrue();

    List<Product> findTop8ByOrderByCreatedAtDesc();

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByStockLessThanEqual(Integer threshold);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchSuggestions(@Param("query") String query);

    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL")
    List<String> findAllBrands();
}
