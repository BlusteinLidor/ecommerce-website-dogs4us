package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderResponseDTO;
import com.ew.ecommercewebsite.model.Order;

import java.time.LocalDate;

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
