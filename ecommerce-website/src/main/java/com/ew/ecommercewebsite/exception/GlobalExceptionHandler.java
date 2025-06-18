package com.ew.ecommercewebsite.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.warn("Email address already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email address already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex){
        log.warn("User not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "User not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(CategoryNotFoundException ex){
        log.warn("Category not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Category not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CategoryNotEmptyException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotEmptyException(CategoryNotEmptyException ex){
        log.warn("Category not empty {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Category is not empty, please delete all products in this category");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex){
        log.warn("Product not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Product not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(OrderItemIdAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleOrderItemIdAlreadyExistsException(OrderItemIdAlreadyExistsException ex){
        log.warn("Order item ID already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Order item ID already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderItemNotFoundException(OrderItemNotFoundException ex){
        log.warn("Order item not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Order item not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CartItemIdAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCartItemIdAlreadyExistsException(CartItemIdAlreadyExistsException ex){
        log.warn("Cart item ID already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Cart item ID already exists");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartItemNotFoundException(CartItemNotFoundException ex){
        log.warn("Cart item not found {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Cart item not found by ID");
        return ResponseEntity.badRequest().body(errors);
    }
}
