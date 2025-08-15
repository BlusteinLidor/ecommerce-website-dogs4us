package com.ew.ecommercewebsite.model;

import com.ew.ecommercewebsite.utils.CartItemId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Entity representing an item in a shopping cart.
 * Each cart item is uniquely identified by a composite key (CartItemId) and contains
 * information about the quantity and any customization preview.
 */
@Entity
public class CartItem {

    /**
     * Composite primary key containing cart and product information.
     */
    @EmbeddedId
    private CartItemId id;

    /**
     * The quantity of the product in the cart.
     * Must not be null.
     */
    @NotNull
    private int quantity;

    /**
     * Preview of any customizations applied to the product.
     * Stored in the database column 'customization_preview'.
     */
    @Column(name = "customization_preview")
    private String customizationPreview;

    /**
     * Gets the quantity of the item in the cart.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the item in the cart.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the composite ID of the cart item.
     *
     * @return the cart item ID
     */
    public CartItemId getId() {
        return id;
    }

    /**
     * Sets the composite ID of the cart item.
     *
     * @param id the cart item ID to set
     */
    public void setId(CartItemId id) {
        this.id = id;
    }

    /**
     * Gets the preview of any customizations applied to the item.
     *
     * @return the customization preview
     */
    public String getCustomizationPreview() {
        return customizationPreview;
    }

    /**
     * Sets the preview of customizations applied to the item.
     *
     * @param customizationPreview the customization preview to set
     */
    public void setCustomizationPreview(String customizationPreview) {
        this.customizationPreview = customizationPreview;
    }



}
