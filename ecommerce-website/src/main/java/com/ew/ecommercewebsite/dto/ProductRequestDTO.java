package com.ew.ecommercewebsite.dto;

import com.ew.ecommercewebsite.model.Category;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProductRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    private String description;

    @NotBlank(message = "Price is required")
    private double price;

    private Category category;

    private int stockQuantity;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getCustomizableFields() {
        return customizableFields;
    }

    public void setCustomizableFields(List<String> customizableFields) {
        this.customizableFields = customizableFields;
    }
}
