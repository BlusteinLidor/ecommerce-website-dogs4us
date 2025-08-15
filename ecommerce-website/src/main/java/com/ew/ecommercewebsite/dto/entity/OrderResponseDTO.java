package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) for representing order response data.
 * This class encapsulates order information for API responses.
 */
public class OrderResponseDTO {

    /**
     * The unique identifier of the order
     */
    private String id;

    /**
     * The user associated with this order
     */
    private String user;

    /**
     * The date when the order was placed
     */
    private String orderDate;

    /**
     * The total monetary amount of the order
     */
    private String totalAmount;

    /**
     * Gets the order identifier
     * @return the order ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the order identifier
     *
     * @param id the order ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user associated with the order
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user associated with the order
     *
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the order date
     *
     * @return the order date
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order date
     *
     * @param orderDate the order date to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the total amount of the order
     *
     * @return the total amount
     */
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order
     *
     * @param totalAmount the total amount to set
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
