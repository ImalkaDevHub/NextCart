package com.ecommerce.app.controller;

import com.ecommerce.app.dto.ReviewDto;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.service.ReviewService;
import com.ecommerce.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addReview(
            @Valid @ModelAttribute("newReview") ReviewDto reviewDto,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        reviewService.addReview(user, reviewDto);
        redirectAttributes.addFlashAttribute("successMessage", "Thank you! Your review has been published.");
        return "redirect:/products/" + reviewDto.getProductId();
    }
}
