package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) that combines cart item information with its associated product details.
 * This class is used for responding to requests that require both cart item and product information.
 */
public class CartItemWithProductResponseDTO {

    /**
     * The cart item information containing user ID, product ID, quantity, and customization preview.
     */
    private CartItemResponseDTO cartItem;

    /**
     * The detailed product information associated with this cart item.
     */
    private ProductResponseDTO product;

    /**
     * Gets the cart item information.
     *
     * @return The CartItemResponseDTO containing cart item details
     */
    public CartItemResponseDTO getCartItem() {
        return cartItem;
    }

    /**
     * Sets the cart item information.
     *
     * @param cartItem The CartItemResponseDTO to set
     */
    public void setCartItem(CartItemResponseDTO cartItem) {
        this.cartItem = cartItem;
    }

    /**
     * Gets the product information.
     *
     * @return The ProductResponseDTO containing product details
     */
    public ProductResponseDTO getProduct() {
        return product;
    }

    /**
     * Sets the product information.
     *
     * @param product The ProductResponseDTO to set
     */
    public void setProduct(ProductResponseDTO product) {
        this.product = product;
    }
}
