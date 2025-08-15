package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.OrderItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderItemResponseDTO;
import com.ew.ecommercewebsite.model.OrderItem;
import com.ew.ecommercewebsite.utils.OrderItemId;

import java.util.UUID;

/**
 * Mapper class responsible for converting between OrderItem entity and its DTO representations.
 */
public class OrderItemMapper {

    /**
     * Converts an OrderItem entity to its DTO representation.
     *
     * @param orderItem The OrderItem entity to convert
     * @return OrderItemResponseDTO containing the converted data
     */
    public static OrderItemResponseDTO toDTO(OrderItem orderItem){
        OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
        orderItemDTO.setOrderId(orderItem.getId().getOrderId().toString());
        orderItemDTO.setProductId(orderItem.getId().getProductId().toString());
        orderItemDTO.setQuantity(String.valueOf(orderItem.getQuantity()));
        orderItemDTO.setUnitPrice(String.valueOf(orderItem.getUnitPrice()));
        orderItemDTO.setCustomizationReference(orderItem.getCustomizationReference());

        return orderItemDTO;
    }

    /**
     * Converts an OrderItemRequestDTO to an OrderItem entity.
     *
     * @param orderItemRequestDTO The DTO containing order item data
     * @return OrderItem entity with the converted data
     */
    public static OrderItem toModel(OrderItemRequestDTO orderItemRequestDTO){
        OrderItem orderItem = new OrderItem();
        OrderItemId orderItemId = new OrderItemId(UUID.fromString(orderItemRequestDTO.getOrderId()), UUID.fromString(orderItemRequestDTO.getProductId()));
        orderItem.setId(orderItemId);
        orderItem.setQuantity(Integer.parseInt(orderItemRequestDTO.getQuantity()));
        orderItem.setUnitPrice(Double.parseDouble(orderItemRequestDTO.getUnitPrice()));
        orderItem.setCustomizationReference(orderItemRequestDTO.getCustomizationReference());

        return orderItem;
    }
}
