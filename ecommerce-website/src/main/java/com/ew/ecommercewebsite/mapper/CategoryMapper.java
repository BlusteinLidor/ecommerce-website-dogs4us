package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.CategoryRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CategoryResponseDTO;
import com.ew.ecommercewebsite.model.Category;

public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category){
        CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
        categoryDTO.setId(category.getId().toString());
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }

    public static Category toModel(CategoryRequestDTO categoryRequestDTO){
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());

        return category;
    }
}
