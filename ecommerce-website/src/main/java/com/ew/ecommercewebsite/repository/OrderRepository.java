package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing Order entities in the database.
 * Extends JpaRepository to inherit basic CRUD operations and custom query methods.
 * The repository handles Order entities with UUID as the primary key type.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
