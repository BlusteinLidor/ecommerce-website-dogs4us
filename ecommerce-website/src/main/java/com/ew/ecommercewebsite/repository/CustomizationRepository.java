package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.Customization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link Customization} entities.
 * Provides CRUD operations and custom queries for Customization data using JPA.
 */
@Repository
public interface CustomizationRepository extends JpaRepository<Customization, UUID> {
    /**
     * Deletes all customizations associated with a specific order.
     *
     * @param orderId The UUID of the order whose customizations should be deleted
     */
    void deleteByOrderOrderId(UUID orderId);
}
