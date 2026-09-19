package com.ecommerce.app.controller;

import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.entity.OrderStatus;
import com.ecommerce.app.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        OrderResponseDto order = orderService.getOrderByOrderNumber(orderService.getAllOrders().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElseThrow().getOrderNumber());
        model.addAttribute("order", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/order-detail";
    }

    @PostMapping("/{id}/status")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status,
            RedirectAttributes redirectAttributes) {

        orderService.updateOrderStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Order status updated to " + status);
        return "redirect:/admin/orders";
    }
}
