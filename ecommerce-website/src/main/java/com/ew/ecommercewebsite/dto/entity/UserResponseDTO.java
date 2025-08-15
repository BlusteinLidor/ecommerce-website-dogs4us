package com.ew.ecommercewebsite.dto.entity;

/**
 * Data Transfer Object (DTO) for sending user information in API responses.
 */
public class UserResponseDTO {

    /**
     * The unique identifier of the user
     */
    private String id;

    /**
     * The name of the user
     */
    private String name;

    /**
     * The email address of the user
     */
    private String email;

    /**
     * Flag indicating whether the user has admin privileges
     */
    private String isAdmin;

    /**
     * Gets the user's unique identifier
     * @return the user ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's unique identifier
     *
     * @param id the user ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the user's name
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email address
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's admin status
     *
     * @return the admin status
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets the user's admin status
     *
     * @param isAdmin the admin status to set
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
