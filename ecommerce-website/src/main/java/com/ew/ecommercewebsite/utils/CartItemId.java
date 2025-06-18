package com.ew.ecommercewebsite.utils;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CartItemId implements Serializable {

    private UUID userId;

    private UUID productId;

    public CartItemId(){}

    public CartItemId(UUID userId, UUID productId){
        this.userId = userId;
        this.productId = productId;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof CartItemId)) return false;
        CartItemId that = (CartItemId) o;
        return Objects.equals(this.userId, that.userId) &&
                Objects.equals(this.productId, that.productId);
    }

    public int hashCode(){
        return Objects.hash(this.userId, this.productId);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
