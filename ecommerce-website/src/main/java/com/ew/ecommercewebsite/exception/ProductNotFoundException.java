package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when a product cannot be found in the system.
 * This runtime exception is typically thrown during product lookup operations
 * when the requested product does not exist in the database.
 */
public class ProductNotFoundException extends RuntimeException {
    /**
     * Constructs a new ProductNotFoundException with the specified detail message.
     *
     * @param message the detail message providing information about why the product was not found
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
