package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when attempting to delete a category that still contains products or subcategories.
 * This runtime exception indicates that the category must be empty before it can be removed.
 */
public class CategoryNotEmptyException extends RuntimeException {
    /**
     * Constructs a new CategoryNotEmptyException with the specified detail message.
     *
     * @param message the detail message explaining why the category cannot be deleted
     */
    public CategoryNotEmptyException(String message) {
        super(message);
    }
}
