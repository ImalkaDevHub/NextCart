package com.ecommerce.app.controller;

import com.ecommerce.app.dto.CartSummaryDto;
import com.ecommerce.app.dto.CheckoutDto;
import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.entity.PaymentMethod;
import com.ecommerce.app.entity.PaymentStatus;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.service.CartService;
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
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    public CheckoutController(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String checkoutPage(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        CartSummaryDto cartSummary = cartService.getCartSummary(user);

        if (cartSummary.getItems() == null || cartSummary.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        CheckoutDto checkoutDto = CheckoutDto.builder()
                .recipientName(user.getFullName())
                .recipientPhone(user.getPhone() != null ? user.getPhone() : "")
                .shippingAddress(user.getAddress() != null ? user.getAddress() : "")
                .paymentMethod(PaymentMethod.COD)
                .build();

        model.addAttribute("checkoutDto", checkoutDto);
        model.addAttribute("cart", cartSummary);
        return "checkout/index";
    }

    @PostMapping("/process")
    public String processCheckout(
            @Valid @ModelAttribute("checkoutDto") CheckoutDto checkoutDto,
            BindingResult bindingResult,
            Model model,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        User user = userService.findByEmail(principal.getName());
        CartSummaryDto cartSummary = cartService.getCartSummary(user);

        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", cartSummary);
            return "checkout/index";
        }

        OrderResponseDto order = orderService.placeOrder(user, checkoutDto);

        if (order.getPaymentStatus() == PaymentStatus.FAILED) {
            redirectAttributes.addFlashAttribute("errorMessage", "Payment Simulation Failed! Please try again or switch payment method.");
            return "redirect:/checkout/payment-failed/" + order.getOrderNumber();
        }

        return "redirect:/checkout/success/" + order.getOrderNumber();
    }

    @GetMapping("/success/{orderNumber}")
    public String orderSuccess(@PathVariable String orderNumber, Model model, Principal principal) {
        OrderResponseDto order = orderService.getOrderByOrderNumber(orderNumber);
        model.addAttribute("order", order);
        return "checkout/success";
    }

    @GetMapping("/payment-failed/{orderNumber}")
    public String paymentFailed(@PathVariable String orderNumber, Model model) {
        OrderResponseDto order = orderService.getOrderByOrderNumber(orderNumber);
        model.addAttribute("order", order);
        return "checkout/payment-failed";
    }
}
