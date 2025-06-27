package com.ew.ecommercewebsite.mapper;

import com.ew.ecommercewebsite.dto.entity.UserRequestDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.model.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user){
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId().toString());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setIsAdmin(String.valueOf(user.getIsAdmin()));

        return userDTO;
    }

    public static User toModel(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(userRequestDTO.getPassword());
        return user;
    }
}
