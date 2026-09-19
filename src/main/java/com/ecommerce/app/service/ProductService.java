package com.ecommerce.app.service;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.ProductSearchFilterDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductDto> searchProducts(ProductSearchFilterDto filter);
    List<ProductDto> getFeaturedProducts();
    List<ProductDto> getLatestProducts();
    ProductDto getProductById(Long id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
    List<ProductDto> getLowStockProducts(Integer threshold);
    List<String> getAllBrands();
    List<ProductDto> searchSuggestions(String query);
    List<ProductDto> getAllProducts();
    void updateStock(Long productId, Integer newStock);
}
