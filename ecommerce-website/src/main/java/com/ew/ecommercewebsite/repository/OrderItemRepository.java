package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.OrderItem;
import com.ew.ecommercewebsite.utils.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link OrderItem} entities in the database.
 * Provides CRUD operations and custom query methods for OrderItem entities.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    /**
     * Deletes all OrderItem entities associated with the specified product ID.
     *
     * @param productId the UUID of the product to delete items for
     */
    void deleteByIdProductId(UUID productId);

    /**
     * Checks if any OrderItem exists for the specified product ID.
     *
     * @param productId the UUID of the product to check
     * @return true if an OrderItem exists for the product ID, false otherwise
     */
    boolean existsByIdProductId(UUID productId);

    /**
     * Checks if any OrderItem exists for the specified order ID.
     *
     * @param orderId the UUID of the order to check
     * @return true if an OrderItem exists for the order ID, false otherwise
     */
    boolean existsByIdOrderId(UUID orderId);

    /**
     * Deletes all OrderItem entities associated with the specified order ID.
     *
     * @param orderId the UUID of the order to delete items for
     */
    void deleteByIdOrderId(UUID orderId);
}
