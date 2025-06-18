package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.OrderItemRequestDTO;
import com.ew.ecommercewebsite.dto.OrderItemResponseDTO;
import com.ew.ecommercewebsite.model.OrderItem;
import com.ew.ecommercewebsite.utils.OrderItemId;

import java.util.UUID;

public class OrderItemMapper {

    public static OrderItemResponseDTO toDTO(OrderItem orderItem){
        OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
        orderItemDTO.setOrderId(orderItem.getId().getOrderId().toString());
        orderItemDTO.setProductId(orderItem.getId().getProductId().toString());
        orderItemDTO.setQuantity(String.valueOf(orderItem.getQuantity()));
        orderItemDTO.setUnitPrice(String.valueOf(orderItem.getUnitPrice()));
        orderItemDTO.setCustomizationReference(orderItem.getCustomizationReference());

        return orderItemDTO;
    }

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
