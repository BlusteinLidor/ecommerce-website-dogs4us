package com.ew.ecommercewebsite.controller;

import com.ew.ecommercewebsite.dto.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.OrderResponseDTO;
import com.ew.ecommercewebsite.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrders(){
        List<OrderResponseDTO> orders = orderService.getOrders();
        return ResponseEntity.ok().body(orders);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderRequestDTO);

        return ResponseEntity.ok().body(orderResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable UUID id, @Validated({Default.class}) @RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.updateOrder(id, orderRequestDTO);

        return ResponseEntity.ok().body(orderResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
