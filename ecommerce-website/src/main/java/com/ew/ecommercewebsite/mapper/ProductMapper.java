package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.ProductRequestDTO;
import com.ew.ecommercewebsite.dto.entity.ProductResponseDTO;
import com.ew.ecommercewebsite.model.Product;

/**
 * Utility class that handles the mapping between Product entities and their DTO representations.
 * This mapper provides conversion methods in both directions - from entity to DTO and vice versa.
 */
public class ProductMapper {

    /**
     * Converts a Product entity to its DTO representation.
     *
     * @param product The Product entity to be converted
     * @return ProductResponseDTO containing the product data
     * @throws NullPointerException if product or its required fields are null
     */
    public static ProductResponseDTO toDTO(Product product){
        ProductResponseDTO productDTO = new ProductResponseDTO();
        productDTO.setId(product.getId().toString());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageURL(product.getImageURL());
        productDTO.setPrice(String.valueOf(product.getPrice()));
        productDTO.setCategory(product.getCategory().getName());
        productDTO.setStockQuantity(String.valueOf(product.getStockQuantity()));
        productDTO.setCustomizableFields(product.getCustomizableFields().toString());

        return productDTO;
    }

    /**
     * Converts a ProductRequestDTO to a Product entity.
     *
     * @param productRequestDTO The DTO containing product data
     * @return Product entity with the data from DTO
     * @throws IllegalArgumentException if price or stock quantity are in invalid format
     * @throws NullPointerException     if productRequestDTO or its required fields are null
     */
    public static Product toModel(ProductRequestDTO productRequestDTO){
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setImageURL(productRequestDTO.getImageURL());

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
