package com.ecommerce.app.controller;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.dto.ReviewDto;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.service.ProductService;
import com.ecommerce.app.service.ReviewService;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final WishlistService wishlistService;

    public ProductController(ProductService productService, ReviewService reviewService, UserService userService, WishlistService wishlistService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model, Principal principal) {
        ProductDto product = productService.getProductById(id);
        List<ReviewDto> reviews = reviewService.getReviewsByProduct(id);

        User currentUser = null;
        boolean inWishlist = false;
        if (principal != null) {
            currentUser = userService.findByEmail(principal.getName());
            inWishlist = wishlistService.isInWishlist(currentUser, id);
        }

        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReview", new ReviewDto());
        model.addAttribute("inWishlist", inWishlist);
        model.addAttribute("relatedProducts", productService.getFeaturedProducts());

        return "product/detail";
    }
}
