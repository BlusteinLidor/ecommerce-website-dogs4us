package com.ew.ecommercewebsite.controller.authentication;

import com.ew.ecommercewebsite.dto.authentication.LoginRequestDTO;
import com.ew.ecommercewebsite.dto.authentication.LoginResponseDTO;
import com.ew.ecommercewebsite.service.authentication.AuthService;
import com.ew.ecommercewebsite.view.SessionUserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.ew.ecommercewebsite.utils.Data.AUTH_HEADER_TOKEN_INDEX;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SessionUserBean sessionUserBean;

    public AuthController(AuthService authService, SessionUserBean sessionUserBean) {
        this.authService = authService;
        this.sessionUserBean = sessionUserBean;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest httpServletRequest){
        LoginResponseDTO loginResponseDTO = authService.authenticate(loginRequestDTO);
//        Optional<String> tokenOpt = authService.authenticate(loginRequestDTO);

        if (loginResponseDTO.getUser() != null){
            sessionUserBean.setUser(loginResponseDTO.getUser());
            return ResponseEntity.ok(loginResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

//        if (tokenOpt.isEmpty()){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        String token = tokenOpt.get();
//        httpServletRequest.getSession().setAttribute("user", token);
////        return ResponseEntity.ok(new LoginResponseDTO(httpSession));
//        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){

        // Authorization: Bearer <token>
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(AUTH_HEADER_TOKEN_INDEX))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
