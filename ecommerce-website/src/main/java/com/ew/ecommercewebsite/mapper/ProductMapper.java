package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.ProductResponseDTO;
import com.ew.ecommercewebsite.dto.UserRequestDTO;
import com.ew.ecommercewebsite.dto.UserResponseDTO;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.model.User;

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
        product.setPrice(productRequestDTO.getPrice());
        product.setCategory(productRequestDTO.getCategory());
        product.setStockQuantity(productRequestDTO.getStockQuantity());
        product.setCustomizableFields(productRequestDTO.getCustomizableFields());

        return product;
    }
}
