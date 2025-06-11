package com.ew.ecommercewebsite.service;

import com.ew.ecommercewebsite.dto.UserRequestDTO;
import com.ew.ecommercewebsite.dto.UserResponseDTO;
import com.ew.ecommercewebsite.mapper.UserMapper;
import com.ew.ecommercewebsite.model.User;
import com.ew.ecommercewebsite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getUsers(){
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(user -> UserMapper.toDTO(user)).toList();

        return userResponseDTOs;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = UserMapper.toModel(userRequestDTO);
        user.setPasswordHash(passwordEncoder
                .encode(userRequestDTO.getPassword()));
        User newUser = userRepository.save(user);

        return UserMapper.toDTO(newUser);
    }
}
