package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.OrderItem;
import com.ew.ecommercewebsite.utils.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
