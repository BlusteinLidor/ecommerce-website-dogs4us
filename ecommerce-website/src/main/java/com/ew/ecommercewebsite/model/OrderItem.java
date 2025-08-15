package com.ew.ecommercewebsite.model;

import com.ew.ecommercewebsite.utils.OrderItemId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an item within an order in the e-commerce system.
 * This entity stores the quantity, unit price, and customization details for products in an order.
 */
@Entity
public class OrderItem {

    /**
     * The composite identifier for this order item.
     * Contains both order and product references.
     */
    @EmbeddedId
    private OrderItemId id;

    /**
     * The quantity of the product ordered.
     * Cannot be null.
     */
    @NotNull
    private int quantity;

    /**
     * The price per unit of the product at the time of order.
     * Cannot be null.
     */
    @NotNull
    private double unitPrice;

    /**
     * Reference to any customization applied to this order item.
     * Can be null if no customization exists.
     */
    private String customizationReference;

    /**
     * Gets the unit price of the product.
     * @return the price per unit
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the product.
     *
     * @param unitPrice the price per unit to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the composite identifier of this order item.
     *
     * @return the order item identifier
     */
    public OrderItemId getId() {
        return id;
    }

    /**
     * Sets the composite identifier of this order item.
     *
     * @param id the order item identifier to set
     */
    public void setId(OrderItemId id) {
        this.id = id;
    }

    /**
     * Gets the quantity of the product ordered.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the reference to any customization for this order item.
     *
     * @return the customization reference
     */
    public String getCustomizationReference() {
        return customizationReference;
    }

    /**
     * Sets the reference to any customization for this order item.
     *
     * @param customizationReference the customization reference to set
     */
    public void setCustomizationReference(String customizationReference) {
        this.customizationReference = customizationReference;
    }

}
