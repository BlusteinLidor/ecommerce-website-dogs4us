package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.OrderResponseDTO;
import com.ew.ecommercewebsite.model.Order;

import java.time.LocalDate;
import java.util.UUID;

public class OrderMapper {

    public static OrderResponseDTO toDTO(Order order){
        OrderResponseDTO orderDTO = new OrderResponseDTO();
        orderDTO.setId(order.getOrderId().toString());
        orderDTO.setUser(order.getUser().getId().toString());
        orderDTO.setOrderDate(order.getOrderDate().toString());
        orderDTO.setTotalAmount(Double.toString(order.getTotalAmount()));

        return orderDTO;
    }

    public static Order toModel(OrderRequestDTO orderRequestDTO){
        Order order = new Order();
        order.setOrderDate(LocalDate.parse(orderRequestDTO.getOrderDate()));
        order.setTotalAmount(Double.parseDouble(orderRequestDTO.getTotalAmount()));

        return order;
    }
}
