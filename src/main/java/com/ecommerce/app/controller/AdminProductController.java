package com.ecommerce.app.controller;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.service.CategoryService;
import com.ecommerce.app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("productDto") ProductDto productDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product-form";
        }

        if (productDto.getId() != null) {
            productService.updateProduct(productDto.getId(), productDto);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        } else {
            productService.createProduct(productDto);
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("infoMessage", "Product deleted.");
        return "redirect:/admin/products";
    }

    @PostMapping("/stock/update")
    public String updateStock(@RequestParam Long productId, @RequestParam Integer stock, RedirectAttributes redirectAttributes) {
        productService.updateStock(productId, stock);
        redirectAttributes.addFlashAttribute("successMessage", "Stock updated successfully!");
        return "redirect:/admin/products";
    }
}
