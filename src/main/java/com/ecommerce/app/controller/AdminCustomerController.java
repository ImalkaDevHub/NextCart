package com.ecommerce.app.controller;

import com.ecommerce.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/customers")
public class AdminCustomerController {

    private final UserService userService;

    public AdminCustomerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", userService.getAllCustomers());
        return "admin/customers";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleCustomerStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.toggleUserStatus(id);
        redirectAttributes.addFlashAttribute("successMessage", "Customer account status updated.");
        return "redirect:/admin/customers";
    }
}
