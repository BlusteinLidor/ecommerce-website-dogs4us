package com.ew.ecommercewebsite.controller.authentication;

import com.ew.ecommercewebsite.dto.authentication.LoginRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.LoginResponseDTO;
import com.ew.ecommercewebsite.dto.authentication.RegisterResponseDTO;
import com.ew.ecommercewebsite.dto.entity.UserRequestDTO;
import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.service.authentication.AuthService;
import com.ew.ecommercewebsite.view.SessionUserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.ew.ecommercewebsite.utils.Data.AUTH_HEADER_TOKEN_INDEX;

/**
 * The AuthController handles user authentication-related operations such as login, registration, and logout.
 * It serves as the entry point for authentication requests in the application.
 * The controller uses AuthService for handling business logic and SessionUserBean to manage session-based user data.
 *
 * @RestController Indicates this class handles RESTful web service requests
 * @RequestMapping("/auth") Maps all authentication related endpoints under /auth
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    /**
     * Service handling authentication business logic
     */
    private final AuthService authService;

    /**
     * Bean managing user session data
     */
    private final SessionUserBean sessionUserBean;


    /**
     * Constructs a new AuthController with the specified authentication service and session bean.
     *
     * @param authService     The service responsible for authentication operations
     * @param sessionUserBean The bean managing user session information
     */
    public AuthController(AuthService authService, SessionUserBean sessionUserBean) {
        this.authService = authService;
        this.sessionUserBean = sessionUserBean;
    }

    /**
     * Authenticates a user and creates a new session.
     *
     * @param loginRequestDTO    Contains user login credentials
     * @param httpServletRequest The HTTP request
     * @return ResponseEntity containing login response with user details and token if successful, or 401 if unauthorized
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest httpServletRequest){
        LoginResponseDTO loginResponseDTO = authService.authenticate(loginRequestDTO);

        if (loginResponseDTO.getUser() != null){
            sessionUserBean.setUser(loginResponseDTO.getUser());
            return ResponseEntity.ok(loginResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    /**
     * Registers a new user in the system.
     *
     * @param userRequestDTO     Contains user registration details
     * @param httpServletRequest The HTTP request
     * @return ResponseEntity containing the created user's details
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO, HttpServletRequest httpServletRequest){
        UserResponseDTO userResponseDTO = authService.register(userRequestDTO);

        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Logs out the current user by invalidating their session.
     *
     * @param httpSession The current HTTP session to be invalidated
     * @return ResponseEntity with no content and cache control headers
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession httpSession){
        sessionUserBean.setUser(null);
        httpSession.invalidate();
        return ResponseEntity.ok().header("Cache-Control", "no-store, no-cache, must-revalidate").build();
    }

}
