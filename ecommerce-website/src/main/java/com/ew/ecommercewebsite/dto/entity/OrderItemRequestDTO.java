package com.ew.ecommercewebsite.dto.entity;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for handling order item requests.
 * This class is used to transfer order item data between different layers of the application.
 */
public class OrderItemRequestDTO {

    /**
     * The unique identifier of the order this item belongs to
     */
    private String orderId;

    /**
     * The unique identifier of the product being ordered
     */
    private String productId;

    /**
     * The quantity of the product being ordered
     * Cannot be blank
     */
    @NotBlank(message = "Quantity is required")
    private String quantity;

    /**
     * The unit price of the product
     * Cannot be blank
     */
    @NotBlank(message = "Unit Price is required")
    private String unitPrice;

    /**
     * Reference to any customization details for the ordered item
     */
    private String customizationReference;

    /**
     * Gets the order ID
     * @return the order ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the order ID
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the product ID
     *
     * @return the product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product ID
     *
     * @param productId the product ID to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Gets the quantity
     *
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unit price
     *
     * @return the unit price
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price
     *
     * @param unitPrice the unit price to set
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the customization reference
     *
     * @return the customization reference
     */
    public String getCustomizationReference() {
        return customizationReference;
    }

    /**
     * Sets the customization reference
     *
     * @param customizationReference the customization reference to set
     */
    public void setCustomizationReference(String customizationReference) {
        this.customizationReference = customizationReference;
    }
}
