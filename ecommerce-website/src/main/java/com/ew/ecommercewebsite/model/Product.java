package com.ew.ecommercewebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Entity class representing a product in the e-commerce system.
 * Products can belong to a category and have customizable fields.
 */
@Entity
public class Product {

    /**
     * Unique identifier for the product
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Name of the product (required)
     */
    @NotNull
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
     * Price of the product (required)
     */
    @NotNull
    private double price;

    /**
     * Category to which this product belongs
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Current available quantity in stock
     */
    @Column(name = "stock_quantity")
    private int stockQuantity;

    /**
     * List of customizable field names available for this product
     */
    @ElementCollection
    @CollectionTable(name = "product_custom_fields", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "field_name")
    private List<String> customizableFields;

    /** @return the product's unique identifier */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the unique identifier to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return the product's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the product's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the product's image URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @param imageURL the image URL to set
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * @return the product's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the product's category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the current stock quantity
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * @param stockQuantity the stock quantity to set
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * @return list of customizable field names
     */
    public List<String> getCustomizableFields() {
        return customizableFields;
    }

    /**
     * @param customizableFields the list of customizable field names to set
     */
    public void setCustomizableFields(List<String> customizableFields) {
        this.customizableFields = customizableFields;
    }
}
