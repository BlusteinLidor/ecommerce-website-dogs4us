package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.OrderRequestDTO;
import com.ew.ecommercewebsite.dto.entity.OrderResponseDTO;
import com.ew.ecommercewebsite.exception.OrderNotFoundException;
import com.ew.ecommercewebsite.exception.UserNotFoundException;
import com.ew.ecommercewebsite.mapper.OrderMapper;
import com.ew.ecommercewebsite.model.Order;
import com.ew.ecommercewebsite.model.User;
import com.ew.ecommercewebsite.repository.CustomizationRepository;
import com.ew.ecommercewebsite.repository.OrderItemRepository;
import com.ew.ecommercewebsite.repository.OrderRepository;
import com.ew.ecommercewebsite.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomizationRepository customizationRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        OrderItemRepository orderItemRepository, CustomizationRepository customizationRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.customizationRepository = customizationRepository;
    }

    public List<OrderResponseDTO> getOrders(){
        List<Order> orders = orderRepository.findAll();

        List<OrderResponseDTO> orderResponseDTOs = orders.stream()
                .map(order -> OrderMapper.toDTO(order)).toList();

        return orderResponseDTOs;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO){
        User user = userRepository.findById(UUID.fromString(orderRequestDTO.getUser()))
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with ID: "
                        + orderRequestDTO.getUser()
                ));

        Order order = OrderMapper.toModel(orderRequestDTO);
        order.setUser(user);
        Order newOrder = orderRepository.save(order);

        return OrderMapper.toDTO(newOrder);
    }

    public OrderResponseDTO updateOrder(UUID id, OrderRequestDTO orderRequestDTO){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with ID: " + id));

        User user = userRepository.findById(UUID.fromString(orderRequestDTO.getUser()))
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with ID: "
                        + orderRequestDTO.getUser()
                ));

        order.setUser(user);
        order.setOrderDate(LocalDate.parse(orderRequestDTO.getOrderDate()));
        order.setTotalAmount(Double.parseDouble(orderRequestDTO.getTotalAmount()));

        Order updatedOrder = orderRepository.save(order);

        return OrderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(UUID id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with ID: " + id));

        orderItemRepository.deleteByIdOrderId(id);
        customizationRepository.deleteByOrderOrderId(id);
        orderRepository.deleteById(id);
    }
}
