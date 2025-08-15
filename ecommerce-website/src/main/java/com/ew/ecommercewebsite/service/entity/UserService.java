package com.ew.ecommercewebsite.service.entity;

import com.ew.ecommercewebsite.dto.entity.UserRequestDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
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

/**
 * Service class handling user-related business operations including CRUD operations and user management.
 */
@Service
public class UserService {

    /**
     * Repository for user data access operations
     */
    private final UserRepository userRepository;
    /**
     * Encoder for password hashing
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Repository for cart item operations
     */
    private final CartItemRepository cartItemRepository;

    /**
     * Constructs a new UserService with required dependencies.
     *
     * @param userRepository     Repository for user operations
     * @param passwordEncoder    Encoder for password hashing
     * @param cartItemRepository Repository for cart item operations
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Retrieves all users from the system.
     *
     * @return List of UserResponseDTO containing user information
     */
    public List<UserResponseDTO> getUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(user -> UserMapper.toDTO(user)).toList();

        return userResponseDTOs;
    }

    /**
     * Creates a new user in the system.
     *
     * @param userRequestDTO DTO containing new user information
     * @return UserResponseDTO containing created user information
     * @throws EmailAlreadyExistsException if email is already registered
     */
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        /* Check that there is no user with the same email already */
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

    /**
     * Updates an existing user's information.
     *
     * @param id             UUID of the user to update
     * @param userRequestDTO DTO containing updated user information
     * @return UserResponseDTO containing updated user information
     * @throws UserNotFoundException       if user is not found
     * @throws EmailAlreadyExistsException if new email is already registered to another user
     */
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with ID: " + id));

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

    /**
     * Deletes a user and their associated cart items from the system.
     *
     * @param id UUID of the user to delete
     * @throws UserNotFoundException if user is not found
     */
    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        cartItemRepository.deleteByIdUserId(id);

        userRepository.delete(user);
    }

}
