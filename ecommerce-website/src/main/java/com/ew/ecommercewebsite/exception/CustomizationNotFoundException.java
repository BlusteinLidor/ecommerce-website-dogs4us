package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when a requested customization cannot be found in the system.
 * This runtime exception is typically used when attempting to access or modify
 * a customization that does not exist in the database.
 */
public class CustomizationNotFoundException extends RuntimeException {
    /**
     * Constructs a new CustomizationNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CustomizationNotFoundException(String message) {
        super(message);
    }
}
