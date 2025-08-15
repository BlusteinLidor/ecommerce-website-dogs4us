package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.CartItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CartItemResponseDTO;
import com.ew.ecommercewebsite.model.CartItem;
import com.ew.ecommercewebsite.utils.CartItemId;

import java.util.UUID;

/**
 * Mapper class responsible for converting between CartItem entity and its DTO representations.
 * This class provides utility methods for bidirectional mapping between CartItem models and DTOs.
 */
public class CartItemMapper {

    /**
     * Converts a CartItem entity to its DTO representation.
     *
     * @param cartItem The CartItem entity to be converted
     * @return CartItemResponseDTO containing the mapped data from the CartItem entity
     */
    public static CartItemResponseDTO toDTO(CartItem cartItem) {
        CartItemResponseDTO cartItemDTO = new CartItemResponseDTO();
        cartItemDTO.setUserId(cartItem.getId().getUserId().toString());
        cartItemDTO.setProductId(cartItem.getId().getProductId().toString());
        cartItemDTO.setQuantity(String.valueOf(cartItem.getQuantity()));
        cartItemDTO.setCustomizationPreview(cartItem.getCustomizationPreview());

        return cartItemDTO;
    }

    /**
     * Converts a CartItemRequestDTO to a CartItem entity.
     *
     * @param cartItemRequestDTO The DTO containing cart item data to be converted
     * @return CartItem entity with the mapped data from the DTO
     */
    public static CartItem toModel(CartItemRequestDTO cartItemRequestDTO) {
        CartItem cartItem = new CartItem();
        CartItemId cartItemId = new CartItemId(UUID.fromString(cartItemRequestDTO.getUserId()), UUID.fromString(cartItemRequestDTO.getProductId()));
        cartItem.setId(cartItemId);
        cartItem.setQuantity(Integer.parseInt(cartItemRequestDTO.getQuantity()));
        cartItem.setCustomizationPreview(cartItemRequestDTO.getCustomizationPreview());

        return cartItem;
    }
}
