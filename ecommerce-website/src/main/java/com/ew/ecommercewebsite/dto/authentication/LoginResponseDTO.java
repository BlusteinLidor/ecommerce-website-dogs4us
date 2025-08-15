package com.ew.ecommercewebsite.dto.authentication;

import com.ew.ecommercewebsite.model.User;
import jakarta.servlet.http.HttpSession;

/**
 * Data Transfer Object (DTO) for handling login response data.
 * This class encapsulates the user information returned after a successful login.
 */
public class LoginResponseDTO {

    /**
     * The authenticated user information.
     */
    private User user;

    /**
     * Default constructor for LoginResponseDTO.
     */
    public LoginResponseDTO(){}

    /**
     * Constructs a LoginResponseDTO with the specified user.
     *
     * @param user the authenticated user information
     */
    public LoginResponseDTO(User user) {
        this.user = user;
    }

    /**
     * Returns the authenticated user information.
     *
     * @return the user information
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the authenticated user information.
     *
     * @param user the user information to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
