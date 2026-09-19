package com.ecommerce.app.controller;

import com.ecommerce.app.dto.CartSummaryDto;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.service.CartService;
import com.ecommerce.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        CartSummaryDto cartSummary = cartService.getCartSummary(user);
        model.addAttribute("cart", cartSummary);
        return "cart/index";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        cartService.addToCart(user, productId, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Product added to shopping cart!");
        return "redirect:/cart";
    }

    @PostMapping("/add-ajax")
    @ResponseBody
    public ResponseEntity<?> addToCartAjax(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Please login to add items to your cart."));
        }

        User user = userService.findByEmail(principal.getName());
        CartSummaryDto updatedCart = cartService.addToCart(user, productId, quantity);
        return ResponseEntity.ok(Map.of(
                "message", "Product added to cart!",
                "totalItems", updatedCart.getTotalItems()
        ));
    }

    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam Long cartItemId,
            @RequestParam Integer quantity,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        cartService.updateCartItemQuantity(user, cartItemId, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Cart updated.");
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(
            @RequestParam Long cartItemId,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        cartService.removeFromCart(user, cartItemId);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart.");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(principal.getName());
        cartService.clearCart(user);
        redirectAttributes.addFlashAttribute("infoMessage", "Cart cleared.");
        return "redirect:/cart";
    }
}
