package com.ecommerce.app.controller;

import com.ecommerce.app.dto.CategoryDto;
import com.ecommerce.app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        return "admin/category-form";
    }

    @PostMapping("/save")
    public String saveCategory(
            @Valid @ModelAttribute("categoryDto") CategoryDto categoryDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "admin/category-form";
        }

        if (categoryDto.getId() != null) {
            categoryService.updateCategory(categoryDto.getId(), categoryDto);
            redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
        } else {
            categoryService.createCategory(categoryDto);
            redirectAttributes.addFlashAttribute("successMessage", "Category created successfully!");
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        model.addAttribute("categoryDto", categoryDto);
        return "admin/category-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Category deleted successfully.");
        return "redirect:/admin/categories";
    }
}
