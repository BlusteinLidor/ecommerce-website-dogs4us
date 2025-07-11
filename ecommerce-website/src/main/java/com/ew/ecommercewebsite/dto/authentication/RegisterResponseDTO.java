package com.ew.ecommercewebsite.dto.authentication;

import com.ew.ecommercewebsite.model.User;

public class RegisterResponseDTO {
    private User user;

    public RegisterResponseDTO(){}

    public RegisterResponseDTO(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }
}
