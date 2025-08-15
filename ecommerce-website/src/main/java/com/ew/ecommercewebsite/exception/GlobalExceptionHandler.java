package com.ew.ecommercewebsite.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides centralized exception handling across all @RequestMapping methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Logger instance for recording exception details and application events.
     */
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation exceptions that occur during request processing.
     * Maps field errors to their corresponding validation messages.
     *
     * @param ex The validation exception containing binding errors
     * @return ResponseEntity containing map of field names to error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where an email address is already registered in the system.
     *
     * @param ex The exception indicating duplicate email address
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.warn("Email address already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email address already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested user cannot be found.
     *
     * @param ex The exception indicating user not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex){
        log.warn("User not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "User not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested category cannot be found.
     *
     * @param ex The exception indicating category not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(CategoryNotFoundException ex){
        log.warn("Category not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Category not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles attempts to delete a category that still contains products.
     *
     * @param ex The exception indicating category is not empty
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(CategoryNotEmptyException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotEmptyException(CategoryNotEmptyException ex){
        log.warn("Category not empty {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Category is not empty, please delete all products in this category");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested product cannot be found.
     *
     * @param ex The exception indicating product not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex){
        log.warn("Product not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Product not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where an order item ID already exists in the system.
     *
     * @param ex The exception indicating duplicate order item ID
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(OrderItemIdAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleOrderItemIdAlreadyExistsException(OrderItemIdAlreadyExistsException ex){
        log.warn("Order item ID already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Order item ID already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested order item cannot be found.
     *
     * @param ex The exception indicating order item not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderItemNotFoundException(OrderItemNotFoundException ex){
        log.warn("Order item not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Order item not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a cart item ID already exists in the system.
     *
     * @param ex The exception indicating duplicate cart item ID
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(CartItemIdAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCartItemIdAlreadyExistsException(CartItemIdAlreadyExistsException ex){
        log.warn("Cart item ID already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Cart item ID already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested cart item cannot be found.
     *
     * @param ex The exception indicating cart item not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartItemNotFoundException(CartItemNotFoundException ex){
        log.warn("Cart item not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Cart item not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested order cannot be found.
     *
     * @param ex The exception indicating order not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundException(OrderNotFoundException ex){
        log.warn("Order not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Order not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles cases where a requested customization cannot be found.
     *
     * @param ex The exception indicating customization not found
     * @return ResponseEntity containing error message
     */
    @ExceptionHandler(CustomizationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomizationNotFoundException(CustomizationNotFoundException ex){
        log.warn("Customization not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Customization not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }
}
