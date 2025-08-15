package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when a requested category cannot be found in the system.
 * This runtime exception is typically thrown during category lookup operations
 * when the specified category does not exist in the database.
 */
public class CategoryNotFoundException extends RuntimeException {
    /**
     * Constructs a new CategoryNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
