package com.ew.ecommercewebsite.utils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a composite identifier for OrderItem entities.
 * This class is used as an embeddable identifier combining orderId and productId.
 */
@Embeddable
public class OrderItemId {

    /**
     * The unique identifier of the order.
     */
    private UUID orderId;

    /**
     * The unique identifier of the product.
     */
    private UUID productId;

    /**
     * Default constructor required by JPA.
     */
    public OrderItemId() {
    }

    /**
     * Creates a new OrderItemId with the specified order and product IDs.
     *
     * @param orderId   The UUID of the order
     * @param productId The UUID of the product
     */
    public OrderItemId(UUID orderId, UUID productId){
        this.orderId = orderId;
        this.productId = productId;
    }

    /**
     * Checks if this OrderItemId is equal to another object.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof OrderItemId)) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(this.orderId, that.orderId) &&
                Objects.equals(this.productId, that.productId);
    }

    /**
     * Generates a hash code for this OrderItemId.
     *
     * @return The hash code value
     */
    public int hashCode(){
        return Objects.hash(this.orderId, this.productId);
    }

    /**
     * Gets the order ID.
     *
     * @return The UUID of the order
     */
    public UUID getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID.
     *
     * @param orderId The UUID to set as the order ID
     */
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the product ID.
     *
     * @return The UUID of the product
     */
    public UUID getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId The UUID to set as the product ID
     */
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
