package com.ecommerce.app.controller;

import com.ecommerce.app.dto.UserRegistrationDto;
import com.ecommerce.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("userRegistrationDto")) {
            model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (dto.getPassword() != null && dto.getConfirmPassword() != null &&
                !dto.getPassword().equals(dto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
            redirectAttributes.addFlashAttribute("userRegistrationDto", dto);
            return "redirect:/register";
        }

        userService.registerUser(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login with your credentials.");
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(String email, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("infoMessage", "If an account exists with " + email + ", a password reset link has been dispatched to your email.");
        return "redirect:/login";
    }
}
