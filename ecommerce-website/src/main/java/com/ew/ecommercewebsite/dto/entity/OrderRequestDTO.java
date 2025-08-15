package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) for handling order request data.
 * This class encapsulates the data needed to create or process an order.
 */
public class OrderRequestDTO {

    /**
     * The identifier or username of the user placing the order
     */
    private String user;

    /**
     * The date when the order is placed
     */
    private String orderDate;

    /**
     * The total monetary amount of the order
     */
    private String totalAmount;

    /**
     * Gets the user identifier
     * @return the user identifier
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user identifier
     *
     * @param user the user identifier to set
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
