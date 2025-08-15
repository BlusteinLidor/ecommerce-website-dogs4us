package com.ew.ecommercewebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a product category in the e-commerce system.
 * This entity is used to categorize products and organize them in the catalog.
 */
@Entity
public class Category {

    /**
     * The unique identifier for the category.
     * Automatically generated using UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The name of the category.
     * Must be unique and cannot be null.
     */
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * Gets the category's unique identifier.
     *
     * @return The UUID of the category
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the category's unique identifier.
     *
     * @param id The UUID to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the category name.
     *
     * @return The name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the category name.
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
