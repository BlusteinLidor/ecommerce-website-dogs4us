package com.ew.ecommercewebsite.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for handling login requests.
 * Contains user credentials required for authentication.
 */
public class LoginRequestDTO {

    /**
     * The email address of the user attempting to log in.
     * Must not be blank and cannot exceed 100 characters.
     */
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    /**
     * The password for user authentication.
     * Must not be blank, minimum length is 8 characters and maximum length is 100 characters.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password cannot exceed 100 characters")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
