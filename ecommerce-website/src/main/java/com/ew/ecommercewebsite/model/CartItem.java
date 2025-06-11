package com.ew.ecommercewebsite.model;

import com.ew.ecommercewebsite.utils.CartItemId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class CartItem {

    @EmbeddedId
    private CartItemId id;

    @NotNull
    private int quantity;

    @Column(name = "customization_preview")
    private String customizationPreview;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItemId getId() {
        return id;
    }

    public void setId(CartItemId id) {
        this.id = id;
    }

    public String getCustomizationPreview() {
        return customizationPreview;
    }

    public void setCustomizationPreview(String customizationPreview) {
        this.customizationPreview = customizationPreview;
    }

}
