package com.ew.ecommercewebsite.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for handling category creation and update requests.
 * Contains validated category information to be processed by the application.
 */
public class CategoryRequestDTO {

    /**
     * The name of the category.
     * Must not be blank and cannot exceed 100 characters.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    /**
     * Gets the name of the category.
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the category name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
