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

/**
 * Service class responsible for managing order item operations including CRUD operations
 * and business logic related to order items in the e-commerce system.
 */
@Service
public class OrderItemService {
    /**
     * Repository for Order Item entities providing data access operations.
     */
    private final OrderItemRepository orderItemRepository;

    /**
     * Constructs an OrderItemService with the specified repository.
     *
     * @param orderItemRepository The repository for order item data access
     */
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Retrieves all order items from the database.
     *
     * @return List of OrderItemResponseDTO containing all order items
     */
    public List<OrderItemResponseDTO> getOrderItems(){
        List<OrderItem> orderItems = orderItemRepository.findAll();

        List<OrderItemResponseDTO> orderItemResponseDTOs = orderItems.stream()
                .map(orderItem -> OrderItemMapper.toDTO(orderItem)).toList();

        return orderItemResponseDTOs;
    }

    /**
     * Creates a new order item based on the provided request data.
     *
     * @param orderItemRequestDTO The DTO containing order item creation data
     * @return OrderItemResponseDTO containing the created order item details
     * @throws OrderItemIdAlreadyExistsException if an order item with the same ID already exists
     */
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO){
        OrderItem orderItem = OrderItemMapper.toModel(orderItemRequestDTO);
        if (orderItemRepository.existsById(orderItem.getId())){
            throw new OrderItemIdAlreadyExistsException("Order item with this ID already exists "
                    + orderItemRequestDTO.getOrderId() + " " + orderItemRequestDTO.getProductId());
        }
        OrderItem newOrderItem = orderItemRepository.save(orderItem);

        return OrderItemMapper.toDTO(newOrderItem);
    }

    /**
     * Updates an existing order item with the provided data.
     *
     * @param id                  The ID of the order item to update
     * @param orderItemRequestDTO The DTO containing updated order item data
     * @return OrderItemResponseDTO containing the updated order item details
     * @throws OrderItemNotFoundException if the order item is not found
     */
    public OrderItemResponseDTO updateOrderItem(OrderItemId id, OrderItemRequestDTO orderItemRequestDTO){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(
                () -> new OrderItemNotFoundException("Order item not found with ID: " + id));

        orderItem.setQuantity(Integer.parseInt(orderItemRequestDTO.getQuantity()));
        orderItem.setUnitPrice(Double.parseDouble(orderItemRequestDTO.getUnitPrice()));
        orderItem.setCustomizationReference(orderItemRequestDTO.getCustomizationReference());

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

        return OrderItemMapper.toDTO(updatedOrderItem);
    }

    /**
     * Deletes an order item by its ID.
     *
     * @param id The ID of the order item to delete
     * @throws OrderItemNotFoundException if the order item is not found
     */
    @Transactional
    public void deleteOrderItem(OrderItemId id){
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(
                () -> new OrderItemNotFoundException("Order item not found with ID: " + id));

        orderItemRepository.delete(orderItem);
    }

    /**
     * Retrieves all order items associated with a specific order.
     *
     * @param orderId The UUID of the order to find items for
     * @return List of OrderItemResponseDTO containing order items for the specified order
     */
    public List<OrderItemResponseDTO> getOrderItemsByOrderId(UUID orderId){
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemResponseDTO> orderItemResponseDTOs = orderItems.stream()
                .filter(orderItem -> orderItem.getId().getOrderId().equals(orderId))
                .map(orderItem -> OrderItemMapper.toDTO(orderItem)).toList();
        return orderItemResponseDTOs;
    }
}
