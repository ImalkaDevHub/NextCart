package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.ProductSearchFilterDto;
import com.ecommerce.app.entity.Category;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.ReviewRepository;
import com.ecommerce.app.service.ProductService;
import com.ecommerce.app.util.FileUploadUtil;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    private final FileUploadUtil fileUploadUtil;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository, FileUploadUtil fileUploadUtil) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public Page<ProductDto> searchProducts(ProductSearchFilterDto filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (filter.getSortBy() != null) {
            switch (filter.getSortBy().toLowerCase()) {
                case "price_low":
                    sort = Sort.by(Sort.Direction.ASC, "price");
                    break;
                case "price_high":
                    sort = Sort.by(Sort.Direction.DESC, "price");
                    break;
                case "newest":
                    sort = Sort.by(Sort.Direction.DESC, "createdAt");
                    break;
                case "popular":
                    sort = Sort.by(Sort.Direction.DESC, "isFeatured");
                    break;
                default:
                    break;
            }
        }

        Pageable pageable = PageRequest.of(
                filter.getPage() != null ? filter.getPage() : 0,
                filter.getSize() != null ? filter.getSize() : 9,
                sort
        );

        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
                String kw = "%" + filter.getKeyword().toLowerCase().trim() + "%";
                Predicate nameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), kw);
                Predicate brandMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), kw);
                Predicate descMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), kw);
                predicates.add(criteriaBuilder.or(nameMatch, brandMatch, descMatch));
            }

            if (filter.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filter.getCategoryId()));
            }

            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), filter.getBrand()));
            }

            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec, pageable).map(this::mapToDto);
    }

    @Override
    public List<ProductDto> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getLatestProducts() {
        return productRepository.findTop8ByOrderByCreatedAtDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return mapToDto(product);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + dto.getCategoryId()));

        String imageName = dto.getImage() != null && !dto.getImage().isBlank() ? dto.getImage() : "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&auto=format&fit=crop&q=60";
        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            try {
                imageName = fileUploadUtil.saveFile(dto.getImageFile());
            } catch (IOException e) {
                log.error("Failed to upload image file: ", e);
            }
        }

        Product product = Product.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .image(imageName)
                .specifications(dto.getSpecifications())
                .isFeatured(dto.getIsFeatured() != null ? dto.getIsFeatured() : false)
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        log.info("Created product: {}", saved.getProductName());
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + dto.getCategoryId()));

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            try {
                String imageName = fileUploadUtil.saveFile(dto.getImageFile());
                product.setImage(imageName);
            } catch (IOException e) {
                log.error("Failed to upload image during product update: ", e);
            }
        } else if (dto.getImage() != null && !dto.getImage().isBlank()) {
            product.setImage(dto.getImage());
        }

        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setSpecifications(dto.getSpecifications());
        product.setIsFeatured(dto.getIsFeatured() != null ? dto.getIsFeatured() : false);
        product.setCategory(category);

        Product updated = productRepository.save(product);
        log.info("Updated product ID: {}", id);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
        log.info("Deleted product ID: {}", id);
    }

    @Override
    public List<ProductDto> getLowStockProducts(Integer threshold) {
        return productRepository.findByStockLessThanEqual(threshold != null ? threshold : 5).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBrands() {
        return productRepository.findAllBrands();
    }

    @Override
    public List<ProductDto> searchSuggestions(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return productRepository.searchSuggestions(query.trim()).stream()
                .limit(5)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        product.setStock(newStock);
        productRepository.save(product);
        log.info("Updated stock for product ID {} to {}", productId, newStock);
    }

    private ProductDto mapToDto(Product product) {
        Double avgRating = reviewRepository.calculateAverageRating(product.getId());
        Integer reviewCount = reviewRepository.countByProductId(product.getId());

        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .price(product.getPrice())
                .stock(product.getStock())
                .image(product.getImage())
                .specifications(product.getSpecifications())
                .isFeatured(product.getIsFeatured())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null)
                .averageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 5.0)
                .reviewCount(reviewCount != null ? reviewCount : 0)
                .build();
    }
}
