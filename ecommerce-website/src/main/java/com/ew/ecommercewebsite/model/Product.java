package com.ew.ecommercewebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private double price;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @ElementCollection
    @CollectionTable(name = "product_custom_fields", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "field_name")
    private List<String> customizableFields;
}
