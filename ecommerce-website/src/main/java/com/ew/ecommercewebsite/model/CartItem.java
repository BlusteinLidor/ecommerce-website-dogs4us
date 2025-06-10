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
    private Object customizationPreview;

}
