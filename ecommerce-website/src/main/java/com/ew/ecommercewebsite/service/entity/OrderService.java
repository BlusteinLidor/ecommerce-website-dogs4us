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
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for handling business logic related to orders in the e-commerce system.
 * Provides functionality for managing orders, including CRUD operations and user-specific order retrieval.
 */
@Service
public class OrderService {
    /**
     * Repository for managing Order entities
     */
    private final OrderRepository orderRepository;
    /**
     * Repository for managing User entities
     */
    private final UserRepository userRepository;
    /**
     * Repository for managing OrderItem entities
     */
    private final OrderItemRepository orderItemRepository;
    /**
     * Repository for managing Customization entities
     */
    private final CustomizationRepository customizationRepository;

    /**
     * Constructs a new OrderService with required repositories.
     *
     * @param orderRepository         Repository for Order entities
     * @param userRepository          Repository for User entities
     * @param orderItemRepository     Repository for OrderItem entities
     * @param customizationRepository Repository for Customization entities
     */
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        OrderItemRepository orderItemRepository, CustomizationRepository customizationRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.customizationRepository = customizationRepository;
    }

    /**
     * Retrieves all orders from the system.
     *
     * @return List of OrderResponseDTO containing all orders
     */
    public List<OrderResponseDTO> getOrders(){
        List<Order> orders = orderRepository.findAll();

        List<OrderResponseDTO> orderResponseDTOs = orders.stream()
                .map(order -> OrderMapper.toDTO(order)).toList();

        return orderResponseDTOs;
    }

    /**
     * Creates a new order in the system.
     *
     * @param orderRequestDTO DTO containing the order details
     * @return OrderResponseDTO containing the created order details
     * @throws UserNotFoundException if the specified user does not exist
     */
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

    /**
     * Updates an existing order.
     *
     * @param id              ID of the order to update
     * @param orderRequestDTO DTO containing updated order details
     * @return OrderResponseDTO containing the updated order details
     * @throws OrderNotFoundException if the order is not found
     * @throws UserNotFoundException  if the specified user does not exist
     */
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

    /**
     * Deletes an order and its associated items and customizations.
     *
     * @param id ID of the order to delete
     * @throws OrderNotFoundException if the order is not found
     */
    @Transactional
    public void deleteOrder(UUID id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with ID: " + id));

        orderItemRepository.deleteByIdOrderId(id);
        customizationRepository.deleteByOrderOrderId(id);
        orderRepository.deleteById(id);
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId ID of the user whose orders to retrieve
     * @return List of OrderResponseDTO containing user's orders
     */
    public List<OrderResponseDTO> getOrdersByUserId(@PathVariable UUID userId){
        List<Order> orders = orderRepository.findAll();

        List<OrderResponseDTO> orderResponseDTOs = orders.stream()
                .map(order -> OrderMapper.toDTO(order)).filter(order -> order.getUser().equalsIgnoreCase(userId.toString())).toList();

        return orderResponseDTOs;
    }
}
