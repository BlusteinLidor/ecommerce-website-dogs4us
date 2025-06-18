package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.CartItemResponseDTO;
import com.ew.ecommercewebsite.model.CartItem;
import com.ew.ecommercewebsite.utils.CartItemId;

import java.util.UUID;

public class CartItemMapper {

    public static CartItemResponseDTO toDTO(CartItem cartItem) {
        CartItemResponseDTO cartItemDTO = new CartItemResponseDTO();
        cartItemDTO.setUserId(cartItem.getId().getUserId().toString());
        cartItemDTO.setProductId(cartItem.getId().getProductId().toString());
        cartItemDTO.setQuantity(String.valueOf(cartItem.getQuantity()));
        cartItemDTO.setCustomizationPreview(cartItem.getCustomizationPreview());

        return cartItemDTO;
    }

    public static CartItem toModel(CartItemRequestDTO cartItemRequestDTO) {
        CartItem cartItem = new CartItem();
        CartItemId cartItemId = new CartItemId(UUID.fromString(cartItemRequestDTO.getUserId()), UUID.fromString(cartItemRequestDTO.getProductId()));
        cartItem.setId(cartItemId);
        cartItem.setQuantity(Integer.parseInt(cartItemRequestDTO.getQuantity()));
        cartItem.setCustomizationPreview(cartItemRequestDTO.getCustomizationPreview());

        return cartItem;
    }
}
