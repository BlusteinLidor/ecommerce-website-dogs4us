package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.Customization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomizationRepository extends JpaRepository<Customization, UUID> {
    void deleteByOrderOrderId(UUID orderId);
}
