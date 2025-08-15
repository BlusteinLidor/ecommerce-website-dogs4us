package com.ew.ecommercewebsite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to perform operations on a product that is out of stock.
 * Returns HTTP 400 Bad Request status code when this exception is thrown in a controller.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductOutOfStockException extends RuntimeException {

    /**
     * Constructs a new ProductOutOfStockException with the specified error message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
