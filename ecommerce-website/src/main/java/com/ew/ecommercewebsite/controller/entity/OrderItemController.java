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

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;

    public OrderItemController(OrderItemService orderItemService, OrderService orderService){
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> getOrderItems(){
        List<OrderItemResponseDTO> orderItems = orderItemService.getOrderItems();
        return ResponseEntity.ok().body(orderItems);
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@Valid @RequestBody OrderItemRequestDTO orderItemRequestDTO){
        OrderItemResponseDTO orderItemResponseDTO = orderItemService.createOrderItem(orderItemRequestDTO);

        return ResponseEntity.ok().body(orderItemResponseDTO);
    }

    @PutMapping("/orderId/{orderId}/productId/{productId}")
    public ResponseEntity<OrderItemResponseDTO> updateOrderItem(@PathVariable UUID orderId, @PathVariable UUID productId, @Validated({Default.class}) @RequestBody OrderItemRequestDTO orderItemRequestDTO){
        OrderItemId id = new OrderItemId(orderId, productId);
        OrderItemResponseDTO orderItemResponseDTO = orderItemService.updateOrderItem(id, orderItemRequestDTO);

        return ResponseEntity.ok().body(orderItemResponseDTO);
    }

    @DeleteMapping("/orderId/{orderId}/productId/{productId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable UUID orderId, @PathVariable UUID productId){
        OrderItemId id = new OrderItemId(orderId, productId);
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<List<OrderItemResponseDTO>> getOrderItemsByOrderId(@PathVariable UUID orderId){
        List<OrderItemResponseDTO> orderItemResponseDTOs = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok().body(orderItemResponseDTOs);
    }
}
