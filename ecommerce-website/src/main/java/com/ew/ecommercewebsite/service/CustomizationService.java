package com.ew.ecommercewebsite.service;

import com.ew.ecommercewebsite.dto.CustomizationRequestDTO;
import com.ew.ecommercewebsite.dto.CustomizationResponseDTO;
import com.ew.ecommercewebsite.exception.CustomizationNotFoundException;
import com.ew.ecommercewebsite.exception.OrderNotFoundException;
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

@Service
public class CustomizationService {
    private final CustomizationRepository customizationRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CustomizationService(CustomizationRepository customizationRepository,
                                ProductRepository productRepository, OrderRepository orderRepository) {
        this.customizationRepository = customizationRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public List<CustomizationResponseDTO> getCustomizations(){
        List<Customization> customizations = customizationRepository.findAll();

        List<CustomizationResponseDTO> customizationResponseDTOs = customizations.stream()
                .map(customization -> CustomizationMapper.toDTO(customization)).toList();

        return customizationResponseDTOs;
    }

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

    public CustomizationResponseDTO updateCustomization(UUID id, CustomizationRequestDTO customizationRequestDTO){
        Customization customization = customizationRepository.findById(id).orElseThrow(
                () -> new CustomizationNotFoundException("Customization not found with ID: " + id));

        Product product = productRepository.findById(UUID.fromString(customizationRequestDTO.getProduct()))
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: "
                + customizationRequestDTO.getProduct()));

        Order order = orderRepository.findById(UUID.fromString(customizationRequestDTO.getOrder()))
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: "
                + customizationRequestDTO.getOrder()));

        customization.setProduct(product);
        customization.setOrder(order);
        customization.setFieldName(customizationRequestDTO.getFieldName());
        customization.setFieldValue(customizationRequestDTO.getFieldValue());

        Customization updatedCustomization = customizationRepository.save(customization);

        return CustomizationMapper.toDTO(updatedCustomization);
    }

    @Transactional
    public void deleteCustomization(UUID id){
        Customization customization = customizationRepository.findById(id).orElseThrow(
                () -> new CustomizationNotFoundException("Customization not found with ID: " + id));

        customizationRepository.deleteById(id);
    }
}
