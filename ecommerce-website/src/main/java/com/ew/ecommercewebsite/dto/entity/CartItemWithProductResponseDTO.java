package com.ew.ecommercewebsite.dto.entity;

public class CartItemWithProductResponseDTO {

    private CartItemResponseDTO cartItem;
    private ProductResponseDTO product;

    public CartItemResponseDTO getCartItem() {
        return cartItem;
    }
    public void setCartItem(CartItemResponseDTO cartItem) {
        this.cartItem = cartItem;
    }
    public ProductResponseDTO getProduct() {
        return product;
    }

    public void setProduct(ProductResponseDTO product) {
        this.product = product;
    }
}
