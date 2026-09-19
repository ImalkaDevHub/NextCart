package com.ecommerce.app.controller;

import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.dto.UserProfileDto;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.service.OrderService;
import com.ecommerce.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String myOrders(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("orders", orderService.getUserOrders(user));
        return "user/orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        OrderResponseDto order = orderService.getOrderByIdAndUser(id, user);
        model.addAttribute("order", order);
        return "user/order-detail";
    }

    @GetMapping("/orders/{id}/invoice")
    public String viewInvoice(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        OrderResponseDto order = orderService.getOrderByIdAndUser(id, user);
        model.addAttribute("order", order);
        return "user/invoice";
    }

    @PostMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByEmail(principal.getName());
        orderService.cancelOrder(id, user);
        redirectAttributes.addFlashAttribute("successMessage", "Order #" + id + " has been cancelled.");
        return "redirect:/orders";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        UserProfileDto userProfile = userService.getUserProfile(principal.getName());
        model.addAttribute("userProfileDto", userProfile);
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @Valid @ModelAttribute("userProfileDto") UserProfileDto profileDto,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "user/profile";
        }

        userService.updateProfile(principal.getName(), profileDto);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/profile";
    }
}
