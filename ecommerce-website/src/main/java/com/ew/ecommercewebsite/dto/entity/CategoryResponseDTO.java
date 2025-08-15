package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) for representing category response data.
 * Used to transfer category information from the server to the client.
 */
public class CategoryResponseDTO {

    /**
     * The unique identifier of the category
     */
    private String id;

    /**
     * The name of the category
     */
    private String name;

    /**
     * Gets the category identifier
     *
     * @return the category id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the category identifier
     *
     * @param id the category id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the category name
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the category name
     *
     * @param name the category name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
