package com.ew.ecommercewebsite.controller.entity;

import com.ew.ecommercewebsite.dto.entity.OrderItemRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderItemResponseDTO;
import com.ew.ecommercewebsite.service.entity.OrderItemService;
import com.ew.ecommercewebsite.service.entity.OrderService;
import com.ew.ecommercewebsite.utils.OrderItemId;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * REST Controller for managing order items in the e-commerce system.
 * Provides endpoints for CRUD operations on order items.
 */
@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    /**
     * Service for handling order item operations
     */
    private final OrderItemService orderItemService;
    /**
     * Service for handling order operations
     */
    private final OrderService orderService;

    /**
     * Constructs a new OrderItemController with required services
     *
     * @param orderItemService service for managing order items
     * @param orderService     service for managing orders
     */
    public OrderItemController(OrderItemService orderItemService, OrderService orderService){
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    /**
     * Retrieves all order items from the system
     *
     * @return ResponseEntity containing a list of all order items
     */
    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> getOrderItems(){
        List<OrderItemResponseDTO> orderItems = orderItemService.getOrderItems();
        return ResponseEntity.ok().body(orderItems);
    }

    /**
     * Creates a new order item
     *
     * @param orderItemRequestDTO the order item data to create
     * @return ResponseEntity containing the created order item
     */
    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@Valid @RequestBody OrderItemRequestDTO orderItemRequestDTO){
        OrderItemResponseDTO orderItemResponseDTO = orderItemService.createOrderItem(orderItemRequestDTO);

        return ResponseEntity.ok().body(orderItemResponseDTO);
    }

    /**
     * Updates an existing order item
     *
     * @param orderId             ID of the order containing the item
     * @param productId           ID of the product in the order item
     * @param orderItemRequestDTO updated order item data
     * @return ResponseEntity containing the updated order item
     */
    @PutMapping("/orderId/{orderId}/productId/{productId}")
    public ResponseEntity<OrderItemResponseDTO> updateOrderItem(@PathVariable UUID orderId, @PathVariable UUID productId, @Validated({Default.class}) @RequestBody OrderItemRequestDTO orderItemRequestDTO){
        OrderItemId id = new OrderItemId(orderId, productId);
        OrderItemResponseDTO orderItemResponseDTO = orderItemService.updateOrderItem(id, orderItemRequestDTO);

        return ResponseEntity.ok().body(orderItemResponseDTO);
    }

    /**
     * Deletes an order item
     *
     * @param orderId   ID of the order containing the item
     * @param productId ID of the product in the order item
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/orderId/{orderId}/productId/{productId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable UUID orderId, @PathVariable UUID productId){
        OrderItemId id = new OrderItemId(orderId, productId);
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all order items for a specific order
     *
     * @param orderId ID of the order to get items for
     * @return ResponseEntity containing a list of order items
     */
    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<List<OrderItemResponseDTO>> getOrderItemsByOrderId(@PathVariable UUID orderId){
        List<OrderItemResponseDTO> orderItemResponseDTOs = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok().body(orderItemResponseDTOs);
    }
}
