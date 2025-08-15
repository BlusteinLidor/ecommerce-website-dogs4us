package com.ew.ecommercewebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Entity class representing customization options for products in orders.
 * This class maintains the relationship between products, orders, and their custom configurations.
 */
@Entity
public class Customization {

    /**
     * Unique identifier for the customization
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The product being customized
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * The order containing this customization
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Name of the customization field
     */
    @NotNull
    private String fieldName;

    /**
     * Value of the customization field
     */
    private String fieldValue;

    /**
     * Gets the name of the customization field
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the name of the customization field
     *
     * @param fieldName the field name to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the unique identifier of the customization
     *
     * @return the customization ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the customization
     *
     * @param id the ID to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets the associated product
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the associated product
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the associated order
     *
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the associated order
     *
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Gets the value of the customization field
     *
     * @return the field value
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the value of the customization field
     *
     * @param fieldValue the field value to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}
