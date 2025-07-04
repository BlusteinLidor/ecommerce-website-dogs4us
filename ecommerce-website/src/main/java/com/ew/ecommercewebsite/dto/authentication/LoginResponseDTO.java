package com.ew.ecommercewebsite.dto.authentication;

import com.ew.ecommercewebsite.model.User;
import jakarta.servlet.http.HttpSession;

public class LoginResponseDTO {

    private User user;

    public LoginResponseDTO(){}

    public LoginResponseDTO(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
