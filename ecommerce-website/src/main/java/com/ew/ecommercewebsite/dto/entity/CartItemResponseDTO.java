package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) for representing cart item response information.
 * This class is used to transfer cart item data between different layers of the application.
 */
public class CartItemResponseDTO {

    /**
     * The unique identifier of the user who owns the cart item
     */
    private String userId;

    /**
     * The unique identifier of the product in the cart
     */
    private String productId;

    /**
     * The quantity of the product in the cart
     */
    private String quantity;

    /**
     * Preview of any customizations applied to the product
     */
    private String customizationPreview;

    /**
     * Gets the user ID associated with the cart item
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the cart item
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the product ID of the cart item
     *
     * @return the product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product ID of the cart item
     *
     * @param productId the product ID to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Gets the quantity of the product in the cart
     *
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the cart
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the customization preview of the cart item
     *
     * @return the customization preview
     */
    public String getCustomizationPreview() {
        return customizationPreview;
    }

    /**
     * Sets the customization preview of the cart item
     *
     * @param customizationPreview the customization preview to set
     */
    public void setCustomizationPreview(String customizationPreview) {
        this.customizationPreview = customizationPreview;
    }
}
