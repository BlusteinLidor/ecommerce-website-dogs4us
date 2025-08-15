package com.ew.ecommercewebsite.controller.entity;

import com.ew.ecommercewebsite.dto.entity.CategoryRequestDTO;
import com.ew.ecommercewebsite.dto.entity.CategoryResponseDTO;
import com.ew.ecommercewebsite.service.entity.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing Category entities.
 * Handles HTTP requests for CRUD operations on categories.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    /**
     * Service layer component for category operations
     */
    private final CategoryService categoryService;

    /**
     * Constructs a new CategoryController with the specified CategoryService.
     *
     * @param categoryService the service layer component for category operations
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves all categories.
     *
     * @return ResponseEntity containing a list of CategoryResponseDTO objects
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
        List<CategoryResponseDTO> categories = categoryService.getCategories();
        return ResponseEntity.ok().body(categories);
    }

    /**
     * Creates a new category.
     *
     * @param categoryRequestDTO the category data to create
     * @return ResponseEntity containing the created CategoryResponseDTO
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);

        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    /**
     * Updates an existing category.
     *
     * @param id                 the UUID of the category to update
     * @param categoryRequestDTO the updated category data
     * @return ResponseEntity containing the updated CategoryResponseDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable UUID id, @Validated({Default.class}) @RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDTO);

        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the UUID of the category to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
