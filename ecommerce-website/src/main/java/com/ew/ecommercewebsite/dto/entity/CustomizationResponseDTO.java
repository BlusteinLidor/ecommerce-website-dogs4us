package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object for representing customization response information.
 */
public class CustomizationResponseDTO {

    /** Unique identifier for the customization */
    private String id;
    /** Reference to the associated product */
    private String product;
    /** Reference to the associated order */
    private String order;
    /** Name of the customization field */
    private String fieldName;
    /** Value of the customization field */
    private String fieldValue;

    /**
     * Gets the customization identifier
     *
     * @return the customization id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the customization identifier
     *
     * @param id the customization id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the associated product reference
     *
     * @return the product reference
     */
    public String getProduct() {
        return product;
    }

    /**
     * Sets the associated product reference
     *
     * @param product the product reference to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Gets the associated order reference
     *
     * @return the order reference
     */
    public String getOrder() {
        return order;
    }

    /**
     * Sets the associated order reference
     *
     * @param order the order reference to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * Gets the customization field name
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the customization field name
     *
     * @param fieldName the field name to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Gets the customization field value
     *
     * @return the field value
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the customization field value
     *
     * @param fieldValue the field value to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
