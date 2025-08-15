package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when attempting to create a category that already exists in the system.
 * This is a runtime exception that indicates a business rule violation.
 */
public class CategoryAlreadyExistsException extends RuntimeException {
  /**
   * Constructs a new CategoryAlreadyExistsException with the specified detail message.
   *
   * @param message the detail message describing the error condition
   */
  public CategoryAlreadyExistsException(String message) {
    super(message);
  }
}
