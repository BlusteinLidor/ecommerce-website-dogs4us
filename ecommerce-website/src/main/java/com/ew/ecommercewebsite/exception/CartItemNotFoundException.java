package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when a cart item cannot be found in the system.
 * This runtime exception is typically thrown during cart operations when attempting to
 * access or modify a cart item that does not exist.
 */
public class CartItemNotFoundException extends RuntimeException {
    /**
     * Constructs a new CartItemNotFoundException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
