package com.ew.ecommercewebsite.dto.entity;

import jakarta.validation.constraints.NotBlank;

public class OrderItemRequestDTO {

    private String orderId;

    private String productId;

    @NotBlank(message = "Quantity is required")
    private String quantity;

    @NotBlank(message = "Unit Price is required")
    private String unitPrice;

    private String customizationReference;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCustomizationReference() {
        return customizationReference;
    }

    public void setCustomizationReference(String customizationReference) {
        this.customizationReference = customizationReference;
    }
}
