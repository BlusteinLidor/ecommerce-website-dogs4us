package com.ew.ecommercewebsite.exception;

/**
 * Exception thrown when attempting to create an order item with an ID that already exists in the system.
 * This runtime exception indicates a violation of unique order item ID constraint.
 */
public class OrderItemIdAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new OrderItemIdAlreadyExistsException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public OrderItemIdAlreadyExistsException(String message) {
    super(message);
  }
}
