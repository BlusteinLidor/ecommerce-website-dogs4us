package com.ew.ecommercewebsite.exception;

public class CartItemIdAlreadyExistsException extends RuntimeException {
  public CartItemIdAlreadyExistsException(String message) {
    super(message);
  }
}
