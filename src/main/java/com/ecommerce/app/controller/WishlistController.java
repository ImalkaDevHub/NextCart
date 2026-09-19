package com.ecommerce.app.controller;

import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.service.UserService;
import com.ecommerce.app.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping
    public String viewWishlist(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<ProductDto> wishlist = wishlistService.getUserWishlist(user);
        model.addAttribute("wishlist", wishlist);
        return "user/wishlist";
    }

    @PostMapping("/add/{productId}")
    public String addToWishlist(@PathVariable Long productId, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(principal.getName());
        wishlistService.addToWishlist(user, productId);
        redirectAttributes.addFlashAttribute("successMessage", "Item added to your Wishlist!");
        return "redirect:/wishlist";
    }

    @PostMapping("/toggle-ajax/{productId}")
    @ResponseBody
    public ResponseEntity<?> toggleWishlistAjax(@PathVariable Long productId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Please login to manage your wishlist."));
        }
        User user = userService.findByEmail(principal.getName());
        boolean exists = wishlistService.isInWishlist(user, productId);
        if (exists) {
            wishlistService.removeFromWishlist(user, productId);
            return ResponseEntity.ok(Map.of("added", false, "message", "Removed from Wishlist"));
        } else {
            wishlistService.addToWishlist(user, productId);
            return ResponseEntity.ok(Map.of("added", true, "message", "Added to Wishlist"));
        }
    }

    @PostMapping("/remove/{productId}")
    public String removeFromWishlist(@PathVariable Long productId, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(principal.getName());
        wishlistService.removeFromWishlist(user, productId);
        redirectAttributes.addFlashAttribute("infoMessage", "Item removed from Wishlist.");
        return "redirect:/wishlist";
    }
}
