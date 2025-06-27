package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.model.Product;

public class ProductMapper {

    public static ProductResponseDTO toDTO(Product product){
        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.setId(product.getId().toString());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(String.valueOf(product.getPrice()));
        productDTO.setCategory(product.getCategory().getName());
        productDTO.setStockQuantity(String.valueOf(product.getStockQuantity()));
        productDTO.setCustomizableFields(product.getCustomizableFields().toString());

        return productDTO;
    }

    public static Product toModel(ProductRequestDTO productRequestDTO){
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());

        try {
            product.setPrice(Double.parseDouble(productRequestDTO.getPrice()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price format: " + productRequestDTO.getPrice());
        }

        try {
            product.setStockQuantity(Integer.parseInt(productRequestDTO.getStockQuantity()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid stock quantity format: " + productRequestDTO.getStockQuantity());
        }

        product.setCustomizableFields(productRequestDTO.getCustomizableFields());

        return product;
    }
}
