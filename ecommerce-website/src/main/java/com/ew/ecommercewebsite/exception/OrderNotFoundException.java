package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when an order cannot be found in the system.
 * This runtime exception is typically thrown during order lookup operations
 * when the requested order does not exist in the database.
 */
public class OrderNotFoundException extends RuntimeException {
    /**
     * Constructs a new OrderNotFoundException with the specified detail message.
     *
     * @param message the detail message describing the error condition
     */
    public OrderNotFoundException(String message) {
        super(message);
    }
}
