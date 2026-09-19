package com.ecommerce.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        log.error("Resource not found exception: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/404";
    }

    @ExceptionHandler(InsufficientStockException.class)
    public String handleInsufficientStock(InsufficientStockException ex, RedirectAttributes redirectAttributes) {
        log.warn("Insufficient stock exception: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/cart";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(UserAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        log.warn("User registration warning: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/register";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        log.error("Unhandled exception caught in GlobalExceptionHandler: ", ex);
        model.addAttribute("errorMessage", ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred.");
        model.addAttribute("errorCode", "500");
        return "error/500";
    }
}
