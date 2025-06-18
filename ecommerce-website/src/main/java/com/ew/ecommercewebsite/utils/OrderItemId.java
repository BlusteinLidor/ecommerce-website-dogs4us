package com.ew.ecommercewebsite.utils;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderItemId {

    private UUID orderId;

    private UUID productId;

    public OrderItemId(){}

    public OrderItemId(UUID orderId, UUID productId){
        this.orderId = orderId;
        this.productId = productId;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof OrderItemId)) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(this.orderId, that.orderId) &&
                Objects.equals(this.productId, that.productId);
    }

    public int hashCode(){
        return Objects.hash(this.orderId, this.productId);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
