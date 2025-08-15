package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.CartItem;
import com.ew.ecommercewebsite.utils.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing CartItem entities in the database.
 * Extends JpaRepository to inherit basic CRUD operations and custom query methods.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    /**
     * Deletes all cart items associated with the specified user ID.
     *
     * @param userId the unique identifier of the user
     */
    void deleteByIdUserId(UUID userId);

    /**
     * Deletes all cart items containing the specified product ID.
     *
     * @param productId the unique identifier of the product
     */
    void deleteByIdProductId(UUID productId);

    /**
     * Retrieves all cart items belonging to the specified user.
     *
     * @param userId the unique identifier of the user
     * @return list of cart items associated with the user
     */
    List<CartItem> findByIdUserId(UUID userId);
}
