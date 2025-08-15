package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) representing a customization request for a product or order.
 * This class encapsulates the data needed to customize a product or modify an order.
 */
public class CustomizationRequestDTO {

    /**
     * The identifier or reference to the product being customized
     */
    private String product;

    /**
     * The identifier or reference to the order being modified
     */
    private String order;

    /**
     * The name of the field that is being customized
     */
    private String fieldName;

    /**
     * The new value to be applied to the customized field
     */
    private String fieldValue;

    /**
     * Gets the product identifier
     * @return the product identifier
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the product identifier
     *
     * @param product the product identifier to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Gets the order identifier
     *
     * @return the order identifier
     */
    public String getOrder() {
        return order;
    }

    /**
     * Sets the order identifier
     *
     * @param order the order identifier to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Gets the name of the field being customized
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the name of the field being customized
     *
     * @param fieldName the field name to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the value to be applied to the customized field
     *
     * @return the field value
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the value to be applied to the customized field
     *
     * @param fieldValue the field value to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
