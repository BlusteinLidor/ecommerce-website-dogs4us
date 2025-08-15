package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.Category;
import com.ew.ecommercewebsite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Product entities.
 * Extends JpaRepository to inherit basic CRUD operations and custom finder methods.
 * Uses UUID as the identifier type for Product entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    /**
     * Finds all products belonging to a specific category.
     *
     * @param category The category to filter products by
     * @return List of products belonging to the specified category
     */
    List<Product> findByCategory(Category category);
}
