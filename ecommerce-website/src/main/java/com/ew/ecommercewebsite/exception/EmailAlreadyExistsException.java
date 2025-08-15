package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when attempting to create or update a user with an email address
 * that already exists in the system.
 */
public class EmailAlreadyExistsException extends RuntimeException {
  /**
   * Constructs a new EmailAlreadyExistsException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public EmailAlreadyExistsException(String message) {
    super(message);
  }
}
