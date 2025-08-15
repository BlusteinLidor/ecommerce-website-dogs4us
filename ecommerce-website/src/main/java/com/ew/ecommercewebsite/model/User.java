package com.ew.ecommercewebsite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing a user in the e-commerce system.
 * This class manages user information including personal details, authentication, and order history.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Unique identifier for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * User's full name
     */
    @NotNull
    private String name;

    /**
     * User's email address, used for authentication and communication
     */
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    /**
     * Hashed version of user's password for secure storage
     */
    @NotNull
    @Column(name = "password_hash")
    private String passwordHash;

    /**
     * Flag indicating whether the user has administrative privileges
     */
    @Column(name = "is_admin")
    private boolean isAdmin;

    /**
     * List of orders associated with this user
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    
    /**
     * @return The user's unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id The user's unique identifier to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return The user's full name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The user's full name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The user's email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The user's hashed password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordHash The user's hashed password to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @return Whether the user has administrative privileges
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param admin Whether the user has administrative privileges
     */
    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }
}
