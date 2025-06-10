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

    private Object customizationReference;
}
