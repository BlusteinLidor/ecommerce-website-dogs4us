package com.ew.ecommercewebsite.controller.entity;


import com.ew.ecommercewebsite.dto.entity.UserRequestDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.service.entity.UserService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing User entities.
 * Provides CRUD operations and other user-related endpoints.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    /**
     * Service to manage User-related business logic. 
     * Acts as a dependency for {@link UserController}.
     * Provides methods to handle User CRUD operations and associated processes.
     */
    private final UserService userService;

    
    /**
     * Constructs a new UserController.
     *
     * @param userService the service to manage User-related business logic
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity containing a list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    /**
     * Creates a new user.
     *
     * @param userRequestDTO the user details to create
     * @return ResponseEntity containing the created user
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);

        return ResponseEntity.ok().body(userResponseDTO);
    }

    /**
     * Updates an existing user.
     *
     * @param id             the ID of the user to update
     * @param userRequestDTO the updated user details
     * @return ResponseEntity containing the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @Validated({Default.class}) @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userRequestDTO);

        return ResponseEntity.ok().body(userResponseDTO);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
