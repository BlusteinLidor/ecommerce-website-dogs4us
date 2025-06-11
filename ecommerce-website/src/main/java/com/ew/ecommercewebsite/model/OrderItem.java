package com.ew.ecommercewebsite.model;

import com.ew.ecommercewebsite.utils.OrderItemId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @NotNull
    private int quantity;

    @NotNull
    private double unitPrice;

    private String customizationReference;

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderItemId getId() {
        return id;
    }

    public void setId(OrderItemId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomizationReference() {
        return customizationReference;
    }

    public void setCustomizationReference(String customizationReference) {
        this.customizationReference = customizationReference;
    }

}
