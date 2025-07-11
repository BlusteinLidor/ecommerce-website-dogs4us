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
import com.ew.ecommercewebsite.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder
            , JwtUtil jwtUtil, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO){
        Optional<User> optUser = userRepository.findByEmail(loginRequestDTO.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash()));

        if (optUser.isPresent()){
            User user = optUser.get();
            return new LoginResponseDTO(user);
        }
        return new LoginResponseDTO(null);
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }

    public boolean validateToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        }
        catch (JwtException e){
            return false;
        }
    }
}
