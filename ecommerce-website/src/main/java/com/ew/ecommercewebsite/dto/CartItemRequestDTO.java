package com.ew.ecommercewebsite.dto;

import jakarta.validation.constraints.NotBlank;

public class CartItemRequestDTO {

    private String userId;

    private String productId;

    @NotBlank(message = "Quantity is required")
    private String quantity;

    private String customizationPreview;

    public String getQuantity() {
        return quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCustomizationPreview() {
        return customizationPreview;
    }

    public void setCustomizationPreview(String customizationPreview) {
        this.customizationPreview = customizationPreview;
    }
}
