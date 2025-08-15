package com.ew.ecommercewebsite.repository;

import com.ew.ecommercewebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing User entities.
 * Extends JpaRepository to inherit basic CRUD operations and custom finder methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Checks if a user with the given email exists in the database.
     *
     * @param email the email address to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user with the given email exists, excluding a specific user ID.
     * Useful for email uniqueness validation during user updates.
     *
     * @param email the email address to check
     * @param id    the user ID to exclude from the check
     * @return true if another user with the email exists, false otherwise
     */
    boolean existsByEmailAndIdNot(String email, UUID id);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
}
