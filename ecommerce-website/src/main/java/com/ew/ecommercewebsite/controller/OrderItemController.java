package com.ew.ecommercewebsite.controller;

import com.ew.ecommercewebsite.dto.OrderItemRequestDTO;
import com.ew.ecommercewebsite.dto.OrderItemResponseDTO;
import com.ew.ecommercewebsite.service.OrderItemService;
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

    public OrderItemController(OrderItemService orderItemService){
        this.orderItemService = orderItemService;
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
}
