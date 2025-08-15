package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.CustomizationRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CustomizationResponseDTO;
import com.ew.ecommercewebsite.exception.CustomizationNotFoundException;
import com.ew.ecommercewebsite.exception.ProductNotFoundException;
import com.ew.ecommercewebsite.mapper.CustomizationMapper;
import com.ew.ecommercewebsite.model.Customization;
import com.ew.ecommercewebsite.model.Order;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CustomizationRepository;
import com.ew.ecommercewebsite.repository.OrderRepository;
import com.ew.ecommercewebsite.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for managing product customization operations.
 * Handles CRUD operations for customizations and their relationships with products and orders.
 */
@Service
public class CustomizationService {
    /**
     * Repository for managing customization entities
     */
    private final CustomizationRepository customizationRepository;
    /**
     * Repository for managing product entities
     */
    private final ProductRepository productRepository;
    /**
     * Repository for managing order entities
     */
    private final OrderRepository orderRepository;

    /**
     * Constructs a new CustomizationService with required repositories.
     *
     * @param customizationRepository Repository for customization operations
     * @param productRepository       Repository for product operations
     * @param orderRepository         Repository for order operations
     */
    public CustomizationService(CustomizationRepository customizationRepository,
                                ProductRepository productRepository, OrderRepository orderRepository) {
        this.customizationRepository = customizationRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves all customizations from the database.
     *
     * @return List of CustomizationResponseDTO containing all customizations
     */
    public List<CustomizationResponseDTO> getCustomizations(){
        List<Customization> customizations = customizationRepository.findAll();

        List<CustomizationResponseDTO> customizationResponseDTOs = customizations.stream()
                .map(customization -> CustomizationMapper.toDTO(customization)).toList();

        return customizationResponseDTOs;
    }

    /**
     * Creates a new customization based on the provided request data.
     *
     * @param customizationRequestDTO The customization data to create
     * @return CustomizationResponseDTO containing the created customization
     * @throws ProductNotFoundException if the specified product is not found
     */
    public CustomizationResponseDTO createCustomization(CustomizationRequestDTO customizationRequestDTO){
        Product product = productRepository.findById(UUID.fromString(customizationRequestDTO.getProduct()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: "
                + customizationRequestDTO.getProduct()));

        Order order = orderRepository.findById(UUID.fromString(customizationRequestDTO.getOrder()))
                .orElseThrow(() -> new ProductNotFoundException("Order not found with ID: "
                + customizationRequestDTO.getOrder()));

        Customization customization = CustomizationMapper.toModel(customizationRequestDTO);
        customization.setProduct(product);
        customization.setOrder(order);
        Customization newCustomization = customizationRepository.save(customization);

        return CustomizationMapper.toDTO(newCustomization);
    }

    /**
     * Updates an existing customization with new data.
     *
     * @param id                      The UUID of the customization to update
     * @param customizationRequestDTO The new customization data
     * @return CustomizationResponseDTO containing the updated customization
     * @throws CustomizationNotFoundException if the customization is not found
     */
    public CustomizationResponseDTO updateCustomization(UUID id, CustomizationRequestDTO customizationRequestDTO){
        Customization customization = customizationRepository.findById(id).orElseThrow(
                () -> new CustomizationNotFoundException("Customization not found with ID: " + id));

//        Product product = productRepository.findById(UUID.fromString(customizationRequestDTO.getProduct()))
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: "
//                + customizationRequestDTO.getProduct()));
//
//        Order order = orderRepository.findById(UUID.fromString(customizationRequestDTO.getOrder()))
//                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: "
//                + customizationRequestDTO.getOrder()));

        customization.setProduct(customization.getProduct());
        customization.setOrder(customization.getOrder());
        customization.setFieldName(customizationRequestDTO.getFieldName());
        customization.setFieldValue(customizationRequestDTO.getFieldValue());

        Customization updatedCustomization = customizationRepository.save(customization);

        return CustomizationMapper.toDTO(updatedCustomization);
    }

    /**
     * Deletes a customization by its ID.
     *
     * @param id The UUID of the customization to delete
     * @throws CustomizationNotFoundException if the customization is not found
     */
    @Transactional
    public void deleteCustomization(UUID id){
        Customization customization = customizationRepository.findById(id).orElseThrow(
                () -> new CustomizationNotFoundException("Customization not found with ID: " + id));

        customizationRepository.deleteById(id);
    }
}
