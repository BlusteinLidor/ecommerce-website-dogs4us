package com.ew.ecommercewebsite.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProductRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    private String description;

    @NotBlank(message = "Price is required")
    private String price;

    private String category;

    private String stockQuantity;

    private List<String> customizableFields;

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

    public List<String> getCustomizableFields() {
        return customizableFields;
    }

    public void setCustomizableFields(List<String> customizableFields) {
        this.customizableFields = customizableFields;
    }
}
