package com.ew.ecommercewebsite.utils;

/**
 * A utility class representing a custom field with a name-value pair.
 * Used for storing and managing custom field data.
 */
public class CustomField {
    /**
     * The name of the custom field
     */
    private String name;
    /**
     * The value associated with the custom field
     */
    private String value;

    /**
     * Constructs a new CustomField with the specified name and value.
     *
     * @param name  the name of the custom field
     * @param value the value of the custom field
     */
    public CustomField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of the custom field.
     *
     * @return the field name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the custom field.
     *
     * @param name the field name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the value of the custom field.
     *
     * @return the field value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the custom field.
     *
     * @param value the field value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
