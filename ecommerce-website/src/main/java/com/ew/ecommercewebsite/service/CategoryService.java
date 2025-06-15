package com.ew.ecommercewebsite.service;

import com.ew.ecommercewebsite.dto.CategoryRequestDTO;
import com.ew.ecommercewebsite.dto.CategoryResponseDTO;
import com.ew.ecommercewebsite.exception.CategoryAlreadyExistsException;
import com.ew.ecommercewebsite.exception.CategoryNotEmptyException;
import com.ew.ecommercewebsite.exception.CategoryNotFoundException;
import com.ew.ecommercewebsite.mapper.CategoryMapper;
import com.ew.ecommercewebsite.model.Category;
import com.ew.ecommercewebsite.model.Product;
import com.ew.ecommercewebsite.repository.CategoryRepository;
import com.ew.ecommercewebsite.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<CategoryResponseDTO> getCategories(){
        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponseDTO> categoryResponseDTOs = categories.stream()
                .map(category -> CategoryMapper.toDTO(category)).toList();

        return categoryResponseDTOs;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        if (categoryRepository.existsByName(categoryRequestDTO.getName())){
            throw new CategoryAlreadyExistsException("A category with this name already exists "
            + categoryRequestDTO.getName());
        }

        Category category = CategoryMapper.toModel(categoryRequestDTO);
        Category newCategory = categoryRepository.save(category);

        return CategoryMapper.toDTO(newCategory);
    }

    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with ID: " + id));

        category.setName(categoryRequestDTO.getName());
        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategory(UUID id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with ID: " + id));

        List<Product> products = productRepository.findByCategory(category);
        if (!products.isEmpty()){
            throw new CategoryNotEmptyException("Category has products associated with it. "
                    + "Please delete the products before deleting the category.");
        }

        categoryRepository.deleteById(id);
    }
}
