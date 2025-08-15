package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.UserRequestDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.model.User;

/**
 * Utility class responsible for mapping between User entities and their corresponding DTOs.
 * This mapper provides methods to convert User models to DTOs and vice versa.
 */
public class UserMapper {

    /**
     * Converts a User entity to its DTO representation.
     *
     * @param user The User entity to be converted
     * @return UserResponseDTO containing the user's data
     */
    public static UserResponseDTO toDTO(User user){
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId().toString());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(String.valueOf(user.getIsAdmin()));

        return userDTO;
    }

    /**
     * Converts a UserRequestDTO to a User entity.
     *
     * @param userRequestDTO The DTO containing user data to be converted
     * @return User entity with the data from the DTO
     */
    public static User toModel(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(userRequestDTO.getPassword());
        return user;
    }
}
