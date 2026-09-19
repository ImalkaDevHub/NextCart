package com.ecommerce.app.controller;

import com.ecommerce.app.dto.CategoryDto;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.ProductSearchFilterDto;
import com.ecommerce.app.service.CategoryService;
import com.ecommerce.app.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductDto> featuredProducts = productService.getFeaturedProducts();
        List<ProductDto> latestProducts = productService.getLatestProducts();
        List<CategoryDto> categories = categoryService.getAllCategories();

        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "newest") String sortBy,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model) {

        ProductSearchFilterDto filter = ProductSearchFilterDto.builder()
                .keyword(keyword)
                .categoryId(categoryId)
                .brand(brand)
                .minPrice(minPrice != null ? java.math.BigDecimal.valueOf(minPrice) : null)
                .maxPrice(maxPrice != null ? java.math.BigDecimal.valueOf(maxPrice) : null)
                .sortBy(sortBy)
                .page(page)
                .size(9)
                .build();

        Page<ProductDto> productPage = productService.searchProducts(filter);

        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", productService.getAllBrands());
        model.addAttribute("filter", filter);
        model.addAttribute("selectedCategory", categoryId != null ? categoryService.getCategoryById(categoryId) : null);

        return "product/list";
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public ResponseEntity<List<ProductDto>> searchSuggestions(@RequestParam String q) {
        return ResponseEntity.ok(productService.searchSuggestions(q));
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "pages/about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "pages/contact";
    }

    @GetMapping("/faq")
    public String faqPage() {
        return "pages/faq";
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "pages/terms";
    }

    @GetMapping("/privacy")
    public String privacyPage() {
        return "pages/privacy";
    }
}
