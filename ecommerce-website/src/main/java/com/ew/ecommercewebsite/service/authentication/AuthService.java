package com.ew.ecommercewebsite.service.authentication;

import com.ew.ecommercewebsite.dto.authentication.LoginRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.LoginResponseDTO;
import com.ew.ecommercewebsite.model.User;
import com.ew.ecommercewebsite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        Optional<User> optUser = userRepository.findByEmail(loginRequestDTO.getEmail());
        if (optUser.isPresent()){
            User user = optUser.get();
            System.out.println(loginRequestDTO.getPassword() + " " + user.getPasswordHash());
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash())){
                loginResponseDTO.setToken(user.getId().toString());
                loginResponseDTO.setMessage("Login successful");
            }
            else{
                loginResponseDTO.setMessage("Invalid email or password");
                loginResponseDTO.setToken(null);
            }
        }
        else{
            loginResponseDTO.setMessage("Invalid email or password");
            loginResponseDTO.setToken(null);
        }
        return loginResponseDTO;
    }
}
