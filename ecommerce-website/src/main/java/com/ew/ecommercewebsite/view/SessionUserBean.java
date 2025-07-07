package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class SessionUserBean implements Serializable {

    private User user;
    private final int cartItemCount = 0;

    public int getCartItemCount() {
        return cartItemCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn(){
        return user != null;
    }
    public void logout(){
        this.user = null;
    }
}
