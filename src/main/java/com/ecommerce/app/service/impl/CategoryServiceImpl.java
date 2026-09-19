package com.ecommerce.app.service.impl;

import com.ecommerce.app.dto.CategoryDto;
import com.ecommerce.app.entity.Category;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return mapToDto(category);
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto dto) {
        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new IllegalArgumentException("Category with name '" + dto.getCategoryName() + "' already exists.");
        }
        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
        Category saved = categoryRepository.save(category);
        log.info("Created category: {}", saved.getCategoryName());
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null && !dto.getImageUrl().isBlank()) {
            category.setImageUrl(dto.getImageUrl());
        }

        Category updated = categoryRepository.save(category);
        log.info("Updated category ID: {}", id);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
        log.info("Deleted category ID: {}", id);
    }

    @Override
    public List<CategoryDto> searchCategories(String name) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(name).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .productCount(category.getProducts() != null ? category.getProducts().size() : 0)
                .build();
    }
}
