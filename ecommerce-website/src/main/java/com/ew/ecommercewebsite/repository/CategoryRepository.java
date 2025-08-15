package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Category entities.
 * Extends JpaRepository to provide CRUD operations for Category entities.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    /**
     * Finds a category by its name.
     *
     * @param name the name of the category to find
     * @return an Optional containing the found Category or empty if not found
     */
    Optional<Category> findByName(String name);

    /**
     * Checks if a category with the given name exists.
     *
     * @param name the name of the category to check
     * @return true if a category with the given name exists, false otherwise
     */
    boolean existsByName(String name);
}
