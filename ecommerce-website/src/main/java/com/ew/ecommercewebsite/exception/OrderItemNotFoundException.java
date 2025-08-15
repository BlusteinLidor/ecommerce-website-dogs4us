package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when an order item cannot be found in the system.
 * This runtime exception is typically thrown during operations that require
 * an existing order item, but the requested item does not exist.
 */
public class OrderItemNotFoundException extends RuntimeException {
    /**
     * Constructs a new OrderItemNotFoundException with the specified error message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
