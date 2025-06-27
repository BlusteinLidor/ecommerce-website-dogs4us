package com.ew.ecommercewebsite.dto.entity;

public class ProductResponseDTO {

    private String id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String stockQuantity;
    private String customizableFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCustomizableFields() {
        return customizableFields;
    }

    public void setCustomizableFields(String customizableFields) {
        this.customizableFields = customizableFields;
    }
}
