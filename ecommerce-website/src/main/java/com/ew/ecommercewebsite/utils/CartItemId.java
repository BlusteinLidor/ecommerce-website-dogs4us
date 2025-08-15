package com.ew.ecommercewebsite.utils;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a composite identifier for cart items, combining user and product IDs.
 * This class is used as an embedded identifier in the cart item entity.
 */
@Embeddable
public class CartItemId implements Serializable {

    /**
     * The unique identifier of the user who owns the cart item.
     */
    private UUID userId;

    /**
     * The unique identifier of the product in the cart item.
     */
    private UUID productId;

    /**
     * Default constructor required by JPA.
     */
    public CartItemId() {
    }

    /**
     * Constructs a new CartItemId with specified user and product IDs.
     *
     * @param userId    The ID of the user
     * @param productId The ID of the product
     */
    public CartItemId(UUID userId, UUID productId){
        this.userId = userId;
        this.productId = productId;
    }

    /**
     * Compares this CartItemId with another object for equality.
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof CartItemId)) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(this.userId, that.userId) &&
                Objects.equals(this.productId, that.productId);
    }

    /**
     * Generates a hash code for this CartItemId.
     * @return A hash code value for this object
     */
    public int hashCode(){
        return Objects.hash(this.userId, this.productId);
    }

    /**
     * Gets the user ID.
     * @return The user ID
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId The user ID to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * Gets the product ID.
     *
     * @return The product ID
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId The product ID to set
     */
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
