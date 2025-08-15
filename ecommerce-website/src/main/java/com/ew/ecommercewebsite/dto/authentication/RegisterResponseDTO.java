package com.ew.ecommercewebsite.dto.authentication;

import com.ew.ecommercewebsite.model.User;

/**
 * Data Transfer Object (DTO) class for handling user registration response.
 * This class encapsulates the user data that is sent back after a successful registration.
 */
public class RegisterResponseDTO {
    /**
     * The registered user entity containing user information.
     */
    private User user;

    /**
     * Default constructor for RegisterResponseDTO.
     * Initializes an empty response object.
     */
    public RegisterResponseDTO(){}

    /**
     * Constructs a RegisterResponseDTO with the specified user.
     *
     * @param user the User entity to be included in the response
     */
    public RegisterResponseDTO(User user){
        this.user = user;
    }

    /**
     * Retrieves the user entity from the response.
     *
     * @return the User entity
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user entity for the response.
     *
     * @param user the User entity to set
     */
    public void setUser(User user){
        this.user = user;
    }
}
