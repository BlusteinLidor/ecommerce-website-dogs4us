package com.ew.ecommercewebsite.controller.authentication;

import com.ew.ecommercewebsite.dto.authentication.LoginRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.LoginResponseDTO;
import com.ew.ecommercewebsite.service.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO authResponseDTO = authService.login(loginRequestDTO);

        return ResponseEntity.ok().body(authResponseDTO);
    }
}
