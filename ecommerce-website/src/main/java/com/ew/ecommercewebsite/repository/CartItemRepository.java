package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.CartItem;
import com.ew.ecommercewebsite.utils.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    void deleteByIdUserId(UUID userId);
    void deleteByIdProductId(UUID productId);
}
