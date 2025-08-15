package com.ew.ecommercewebsite.controller.entity;

import com.ew.ecommercewebsite.dto.entity.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderResponseDTO;
import com.ew.ecommercewebsite.service.entity.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing Order operations.
 * Provides endpoints for CRUD operations on orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     * Service layer for order operations
     */
    private final OrderService orderService;

    /**
     * Constructs an OrderController with the specified OrderService
     *
     * @param orderService the service to handle order operations
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves all orders
     *
     * @return ResponseEntity containing a list of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(){
        List<OrderResponseDTO> orders = orderService.getOrders();
        return ResponseEntity.ok().body(orders);
    }

    /**
     * Creates a new order
     *
     * @param orderRequestDTO the order data to create
     * @return ResponseEntity containing the created order
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderRequestDTO);

        return ResponseEntity.ok().body(orderResponseDTO);
    }

    /**
     * Updates an existing order
     *
     * @param id              the ID of the order to update
     * @param orderRequestDTO the updated order data
     * @return ResponseEntity containing the updated order
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable UUID id, @Validated({Default.class}) @RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.updateOrder(id, orderRequestDTO);

        return ResponseEntity.ok().body(orderResponseDTO);
    }

    /**
     * Deletes an order
     *
     * @param id the ID of the order to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all orders for a specific user
     *
     * @param userId the ID of the user whose orders to retrieve
     * @return ResponseEntity containing a list of the user's orders
     */
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable UUID userId){
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok().body(orders);
    }
}
