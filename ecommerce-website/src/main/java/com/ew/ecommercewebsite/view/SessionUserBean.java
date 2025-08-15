package com.ew.ecommercewebsite.view;

import com.ew.ecommercewebsite.dto.entity.UserResponseDTO;
import com.ew.ecommercewebsite.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Named;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Bean class that manages user session information.
 * This class is session-scoped and maintains user state throughout their session.
 */
@Named
@Component
@SessionScoped
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionUserBean implements Serializable {
    /**
     * The currently logged-in user, or null if no user is logged in
     */
    private User user;

    /**
     * The number of items in the user's shopping cart
     */
    private final int cartItemCount = 0;

    /**
     * Returns the number of items in the user's shopping cart
     *
     * @return number of items in cart
     */
    public int getCartItemCount() {
        return cartItemCount;
    }

    /**
     * Gets the currently logged-in user
     *
     * @return the current User object, or null if no user is logged in
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the currently logged-in user
     *
     * @param user the User to set as currently logged in
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Checks if a user is currently logged in
     *
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return user != null;
    }

    /**
     * Logs out the current user by setting the user reference to null
     */
    public void logout(){
        this.user = null;
    }
}
