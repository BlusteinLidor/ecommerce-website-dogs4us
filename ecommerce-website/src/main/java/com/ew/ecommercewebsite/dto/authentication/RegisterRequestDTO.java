package com.ew.ecommercewebsite.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for handling user registration requests.
 * Contains user registration information including name, email, and password.
 */
public class RegisterRequestDTO {

    /**
     * The user's full name.
     * Cannot be blank and must not exceed 100 characters.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    /**
     * The user's email address.
     * Cannot be blank and must not exceed 100 characters.
     */
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    /**
     * The user's password.
     * Cannot be blank, must be at least 8 characters and must not exceed 100 characters.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password cannot exceed 100 characters")
    private String password;

    /**
     * Gets the user's name.
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
