package com.ew.ecommercewebsite.service;

import com.ew.ecommercewebsite.dto.UserRequestDTO;
import com.ew.ecommercewebsite.dto.UserResponseDTO;
import com.ew.ecommercewebsite.exception.EmailAlreadyExistsException;
import com.ew.ecommercewebsite.exception.UserNotFoundException;
import com.ew.ecommercewebsite.mapper.UserMapper;
import com.ew.ecommercewebsite.model.User;
import com.ew.ecommercewebsite.repository.CartItemRepository;
import com.ew.ecommercewebsite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartItemRepository cartItemRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartItemRepository = cartItemRepository;
    }

    public List<UserResponseDTO> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(user -> UserMapper.toDTO(user)).toList();

        return userResponseDTOs;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A user with this email already exists "
                    + userRequestDTO.getEmail());
        }

        User user = UserMapper.toModel(userRequestDTO);
        user.setPasswordHash(passwordEncoder
                .encode(userRequestDTO.getPassword()));
        User newUser = userRepository.save(user);

        return UserMapper.toDTO(newUser);
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        if (userRepository.existsByEmailAndIdNot(userRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A user with this email already exists "
                    + userRequestDTO.getEmail());
        }

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        cartItemRepository.deleteByIdUserId(id);

        userRepository.delete(user);
    }

}
