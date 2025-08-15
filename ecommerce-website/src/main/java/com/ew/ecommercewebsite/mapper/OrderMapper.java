package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderResponseDTO;
import com.ew.ecommercewebsite.model.Order;

import java.time.LocalDate;

/**
 * Mapper class responsible for converting Order entities between DTOs and Model objects.
 * This class provides utility methods for bidirectional mapping of Order objects.
 */
public class OrderMapper {

    /**
     * Converts an Order model object to its DTO representation.
     *
     * @param order the Order model object to be converted
     * @return OrderResponseDTO containing the order data
     */
    public static OrderResponseDTO toDTO(Order order){
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setId(order.getOrderId().toString());
        orderDTO.setUser(order.getUser().getId().toString());
        orderDTO.setOrderDate(order.getOrderDate().toString());
        orderDTO.setTotalAmount(Double.toString(order.getTotalAmount()));

        return orderDTO;
    }

    /**
     * Converts an OrderRequestDTO to its corresponding Order model object.
     *
     * @param orderRequestDTO the DTO containing order data to be converted
     * @return Order model object containing the order data
     */
    public static Order toModel(OrderRequestDTO orderRequestDTO){
        Order order = new Order();
        order.setOrderDate(LocalDate.parse(orderRequestDTO.getOrderDate()));
        order.setTotalAmount(Double.parseDouble(orderRequestDTO.getTotalAmount()));

        return order;
    }
}
