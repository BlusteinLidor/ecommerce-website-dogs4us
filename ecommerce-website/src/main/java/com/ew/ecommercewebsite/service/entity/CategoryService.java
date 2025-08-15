package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.CategoryRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CategoryResponseDTO;
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

/**
 * Service class responsible for managing product categories.
 * Provides functionality for CRUD operations on categories.
 */
@Service
public class CategoryService {

    /**
     * Repository for category data access operations
     */
    private final CategoryRepository categoryRepository;

    /**
     * Repository for product data access operations
     */
    private final ProductRepository productRepository;

    /**
     * Constructs a new CategoryService with required repositories
     *
     * @param categoryRepository repository for category operations
     * @param productRepository  repository for product operations
     */
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all categories from the database
     * @return List of CategoryResponseDTO containing all categories
     */
    public List<CategoryResponseDTO> getCategories(){
        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponseDTO> categoryResponseDTOs = categories.stream()
                .map(category -> CategoryMapper.toDTO(category)).toList();

        return categoryResponseDTOs;
    }

    /**
     * Creates a new category
     * @param categoryRequestDTO the category data to create
     * @return CategoryResponseDTO containing the created category data
     * @throws CategoryAlreadyExistsException if a category with the same name already exists
     */
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        if (categoryRepository.existsByName(categoryRequestDTO.getName())){
            throw new CategoryAlreadyExistsException("A category with this name already exists "
            + categoryRequestDTO.getName());
        }

        Category category = CategoryMapper.toModel(categoryRequestDTO);
        Category newCategory = categoryRepository.save(category);

        return CategoryMapper.toDTO(newCategory);
    }

    /**
     * Updates an existing category
     * @param id the UUID of the category to update
     * @param categoryRequestDTO the new category data
     * @return CategoryResponseDTO containing the updated category data
     * @throws CategoryNotFoundException if the category with given ID is not found
     */
    public CategoryResponseDTO updateCategory(UUID id, CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with ID: " + id));

        category.setName(categoryRequestDTO.getName());
        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toDTO(updatedCategory);
    }

    /**
     * Deletes a category by its ID
     * @param id the UUID of the category to delete
     * @throws CategoryNotFoundException if the category with given ID is not found
     * @throws CategoryNotEmptyException if the category has associated products
     */
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
