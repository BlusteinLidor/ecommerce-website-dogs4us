package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) representing a product response in the e-commerce system.
 * This class contains product information to be sent to the client.
 */
public class ProductResponseDTO {

    /**
     * Unique identifier of the product
     */
    private String id;
    /**
     * Name of the product
     */
    private String name;
    /**
     * Detailed description of the product
     */
    private String description;
    /**
     * URL pointing to the product's image
     */
    private String imageURL;
    /**
     * Price of the product
     */
    private String price;
    /**
     * Category to which the product belongs
     */
    private String category;
    /**
     * Available quantity in stock
     */
    private String stockQuantity;
    /**
     * JSON string containing customizable fields for the product
     */
    private String customizableFields;

    /**
     * Gets the product's unique identifier
     *
     * @return the product ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the product's unique identifier
     *
     * @param id the product ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the product name
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description
     *
     * @param description the product description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product image URL
     *
     * @return the product image URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the product image URL
     *
     * @param imageURL the product image URL to set
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Gets the product price
     *
     * @return the product price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the product price
     *
     * @param price the product price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets the product category
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category
     *
     * @param category the product category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the product stock quantity
     *
     * @return the product stock quantity
     */
    public String getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the product stock quantity
     *
     * @param stockQuantity the product stock quantity to set
     */
    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the product customizable fields
     *
     * @return JSON string containing customizable fields
     */
    public String getCustomizableFields() {
        return customizableFields;
    }

    /**
     * Sets the product customizable fields
     *
     * @param customizableFields JSON string containing customizable fields to set
     */
    public void setCustomizableFields(String customizableFields) {
        this.customizableFields = customizableFields;
    }
}
