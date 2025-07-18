package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.OrderItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderItemResponseDTO;
import com.ew.ecommercewebsite.exception.OrderItemIdAlreadyExistsException;
import com.ew.ecommercewebsite.exception.OrderItemNotFoundException;
import com.ew.ecommercewebsite.mapper.OrderItemMapper;
import com.ew.ecommercewebsite.model.OrderItem;
import com.ew.ecommercewebsite.repository.OrderItemRepository;
import com.ew.ecommercewebsite.utils.OrderItemId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItemResponseDTO> getOrderItems(){
        List<OrderItem> orderItems = orderItemRepository.findAll();

        List<OrderItemResponseDTO> orderItemResponseDTOs = orderItems.stream()
                .map(orderItem -> OrderItemMapper.toDTO(orderItem)).toList();

        return orderItemResponseDTOs;
    }

    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO){
        OrderItem orderItem = OrderItemMapper.toModel(orderItemRequestDTO);
        if (orderItemRepository.existsById(orderItem.getId())){
            throw new OrderItemIdAlreadyExistsException("Order item with this ID already exists "
                    + orderItemRequestDTO.getOrderId() + " " + orderItemRequestDTO.getProductId());
        }
        OrderItem newOrderItem = orderItemRepository.save(orderItem);

        return OrderItemMapper.toDTO(newOrderItem);
    }

    public OrderItemResponseDTO updateOrderItem(OrderItemId id, OrderItemRequestDTO orderItemRequestDTO){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(
                () -> new OrderItemNotFoundException("Order item not found with ID: " + id));

        orderItem.setQuantity(Integer.parseInt(orderItemRequestDTO.getQuantity()));
        orderItem.setUnitPrice(Double.parseDouble(orderItemRequestDTO.getUnitPrice()));
        orderItem.setCustomizationReference(orderItemRequestDTO.getCustomizationReference());

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

        return OrderItemMapper.toDTO(updatedOrderItem);
    }

    @Transactional
    public void deleteOrderItem(OrderItemId id){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(
                () -> new OrderItemNotFoundException("Order item not found with ID: " + id));

        orderItemRepository.delete(orderItem);
    }

    public List<OrderItemResponseDTO> getOrderItemsByOrderId(UUID orderId){
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemResponseDTO> orderItemResponseDTOs = orderItems.stream()
                .filter(orderItem -> orderItem.getId().getOrderId().equals(orderId))
                .map(orderItem -> OrderItemMapper.toDTO(orderItem)).toList();
        return orderItemResponseDTOs;
    }
}
