package com.ew.ecommercewebsite.service;

import com.ew.ecommercewebsite.dto.ProductResponseDTO;
import com.ew.ecommercewebsite.mapper.ProductMapper;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(product -> ProductMapper.toDTO(product)).toList();

        return productResponseDTOs;
    }
}
