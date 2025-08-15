package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) representing response data for an order item in the e-commerce system.
 * Contains information about ordered products, quantities, prices, and customization details.
 */
public class OrderItemResponseDTO {

    /**
     * The unique identifier of the order this item belongs to
     */
    private String orderId;
    /**
     * The unique identifier of the product in this order item
     */
    private String productId;
    /**
     * The quantity of products ordered in this item
     */
    private String quantity;
    /**
     * The unit price of the product at the time of order
     */
    private String unitPrice;
    /**
     * Reference to any customization applied to the product
     */
    private String customizationReference;

    /**
     * Gets the order ID
     *
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
