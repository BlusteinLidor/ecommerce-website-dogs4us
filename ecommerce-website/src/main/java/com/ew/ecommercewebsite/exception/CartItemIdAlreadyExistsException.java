package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when attempting to create a cart item with an ID that already exists in the system.
 * This runtime exception indicates a violation of unique constraint for cart item identifiers.
 */
public class CartItemIdAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new CartItemIdAlreadyExistsException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public CartItemIdAlreadyExistsException(String message) {
    super(message);
  }
}
