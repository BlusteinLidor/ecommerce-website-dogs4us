package com.ew.ecommercewebsite.service.authentication;

import com.ew.ecommercewebsite.dto.authentication.LoginRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.LoginResponseDTO;
import com.ew.ecommercewebsite.dto.authentication.RegisterRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.RegisterResponseDTO;
import com.ew.ecommercewebsite.dto.entity.UserRequestDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.model.User;
import com.ew.ecommercewebsite.repository.UserRepository;
import com.ew.ecommercewebsite.service.entity.UserService;
//import com.ew.ecommercewebsite.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class handling authentication-related operations including user login and registration.
 */
@Service
public class AuthService {

    /**
     * Repository for user data access operations
     */
    private final UserRepository userRepository;

    /**
     * Encoder for password hashing and verification
     */
    private final PasswordEncoder passwordEncoder;

//    private final JwtUtil jwtUtil;

    /**
     * Service for user-related operations
     */
    private final UserService userService;

    /**
     * Constructs an AuthService with necessary dependencies
     *
     * @param userRepository   repository for user operations
     * @param passwordEncoder  encoder for password operations
     * @param userService      service for user-related operations
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder
            , UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * Authenticates a user based on login credentials
     *
     * @param loginRequestDTO the login credentials containing email and password
     * @return LoginResponseDTO containing user information if authentication successful, null otherwise
     */
    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO){
        Optional<User> optUser = userRepository.findByEmail(loginRequestDTO.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash()));

        if (optUser.isPresent()){
            User user = optUser.get();
            return new LoginResponseDTO(user);
        }
        return new LoginResponseDTO(null);
    }

    /**
     * Registers a new user in the system
     *
     * @param userRequestDTO the user registration data
     * @return UserResponseDTO containing the created user information
     */
    public UserResponseDTO register(UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }

//    public boolean validateToken(String token){
//        try{
//            jwtUtil.validateToken(token);
//            return true;
//        }
//        catch (JwtException e){
//            return false;
//        }
//    }
}
