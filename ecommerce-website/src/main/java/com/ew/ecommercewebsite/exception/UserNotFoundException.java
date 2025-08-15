package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when a user cannot be found in the system.
 * This runtime exception is typically thrown during user lookup operations
 * when the requested user does not exist in the database.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
