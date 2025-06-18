package com.ew.ecommercewebsite.exception;

public class OrderItemIdAlreadyExistsException extends RuntimeException {
  public OrderItemIdAlreadyExistsException(String message) {
    super(message);
  }
}
