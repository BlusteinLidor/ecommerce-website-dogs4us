package com.ew.ecommercewebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity representing an order in the e-commerce system.
 * Each order is associated with a user and contains information about the purchase.
 */
@Entity
@Table(name = "orders")
public class Order {

    /**
     * Unique identifier for the order
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    /**
     * User who placed the order
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Date when the order was placed
     */
    @NotNull
    private LocalDate orderDate;

    /**
     * Total amount of the order
     */
    @NotNull
    private double totalAmount;
    
    /**
     * Gets the user who placed the order
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who placed the order
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the order's unique identifier
     *
     * @return the order ID
     */
    public UUID getOrderId() {
        return orderId;
    }

    /**
     * Sets the order's unique identifier
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the date when the order was placed
     *
     * @return the order date
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date when the order was placed
     *
     * @param orderDate the order date to set
     */
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Gets the total amount of the order
     *
     * @return the total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order
     *
     * @param totalAmount the total amount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
