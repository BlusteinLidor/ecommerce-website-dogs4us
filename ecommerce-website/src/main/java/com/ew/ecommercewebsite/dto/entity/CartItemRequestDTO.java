package com.ew.ecommercewebsite.dto.entity;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for handling cart item requests in the e-commerce system.
 * This class encapsulates the data required for cart item operations such as adding or updating items in a shopping cart.
 */
public class CartItemRequestDTO {

    /**
     * The unique identifier of the user who owns the cart.
     */
    private String userId;

    /**
     * The unique identifier of the product being added to the cart.
     */
    private String productId;

    /**
     * The quantity of the product to be added to the cart.
     * This field cannot be blank.
     */
    @NotBlank(message = "Quantity is required")
    private String quantity;

    /**
     * Preview of any customizations applied to the product.
     * This field is optional and can be null.
     */
    private String customizationPreview;

    /**
     * Gets the quantity of the product.
     * @return the quantity as a string
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     *
     * @param productId the product ID to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the customization preview.
     *
     * @return the customization preview
     */
    public String getCustomizationPreview() {
        return customizationPreview;
    }

    /**
     * Sets the customization preview.
     *
     * @param customizationPreview the customization preview to set
     */
    public void setCustomizationPreview(String customizationPreview) {
        this.customizationPreview = customizationPreview;
    }
}
