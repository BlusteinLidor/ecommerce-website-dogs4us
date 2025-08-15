package com.ew.ecommercewebsite.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Data Transfer Object for product creation and update requests.
 * Contains all necessary fields to create or modify a product in the system.
 */
public class ProductRequestDTO {

    /**
     * The name of the product. Cannot be blank and must not exceed 100 characters.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    /**
     * The detailed description of the product.
     */
    private String description;

    /**
     * The URL pointing to the product's image.
     */
    private String imageURL;

    /**
     * The price of the product. Cannot be blank.
     */
    @NotBlank(message = "Price is required")
    private String price;

    /**
     * The category to which the product belongs.
     */
    private String category;

    /**
     * The available quantity of the product in stock.
     */
    private String stockQuantity;

    /**
     * List of fields that can be customized for this product.
     */
    private List<String> customizableFields;

    /**
     * Gets the product name.
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the product description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product image URL.
     *
     * @return the product image URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the product image URL.
     *
     * @param imageURL the product image URL to set
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the product price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets the product category.
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the product category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the product stock quantity.
     *
     * @return the product stock quantity
     */
    public String getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the product stock quantity.
     *
     * @param stockQuantity the product stock quantity to set
     */
    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets the list of customizable fields for the product.
     *
     * @return the list of customizable fields
     */
    public List<String> getCustomizableFields() {
        return customizableFields;
    }

    /**
     * Sets the list of customizable fields for the product.
     *
     * @param customizableFields the list of customizable fields to set
     */
    public void setCustomizableFields(List<String> customizableFields) {
        this.customizableFields = customizableFields;
    }
}
