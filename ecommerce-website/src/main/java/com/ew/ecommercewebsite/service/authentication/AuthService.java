package com.ew.ecommercewebsite.service.authentication;

import com.ew.ecommercewebsite.dto.authentication.LoginRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.LoginResponseDTO;
import com.ew.ecommercewebsite.model.User;
import com.ew.ecommercewebsite.repository.UserRepository;
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

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

//    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
//        Optional<String> token = userRepository.findByEmail(loginRequestDTO.getEmail())
//                .filter(user -> passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash()))
//                .map(user -> jwtUtil.generateToken(user.getEmail(), Boolean.toString(user.getIsAdmin())));
//
//        return token;
//    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO){
        Optional<User> optUser = userRepository.findByEmail(loginRequestDTO.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash()));

        if (optUser.isPresent()){
            User user = optUser.get();
            return new LoginResponseDTO(user);
        }
        return new LoginResponseDTO(null);
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
