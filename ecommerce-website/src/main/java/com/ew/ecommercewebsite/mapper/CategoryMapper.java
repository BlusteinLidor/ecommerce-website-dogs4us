package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.CategoryRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CategoryResponseDTO;
import com.ew.ecommercewebsite.model.Category;

/**
 * Mapper class for converting between Category model and DTOs.
 * Provides utility methods for mapping Category entities to DTOs and vice versa.
 */
public class CategoryMapper {

    /**
     * Converts a Category entity to a CategoryResponseDTO.
     *
     * @param category The Category entity to convert
     * @return A CategoryResponseDTO containing the mapped data
     */
    public static CategoryResponseDTO toDTO(Category category){
        CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
        categoryDTO.setId(category.getId().toString());
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }

    /**
     * Converts a CategoryRequestDTO to a Category entity.
     *
     * @param categoryRequestDTO The DTO containing category data
     * @return A Category entity containing the mapped data
     */
    public static Category toModel(CategoryRequestDTO categoryRequestDTO){
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());

        return category;
    }
}
