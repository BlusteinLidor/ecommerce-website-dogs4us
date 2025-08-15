package com.ew.ecommercewebsite.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

/**
 * Bean class responsible for managing the login page functionality.
 * This class is request-scoped and handles user session management and redirections.
 */
@Named
@RequestScoped
public class LoginPageBean {

    /**
     * The session bean that maintains user login state throughout the session.
     */
    private final SessionUserBean sessionUserBean;

    /**
     * Constructs a new LoginPageBean with the specified session user bean.
     *
     * @param sessionUserBean The session bean to manage user state
     */
    @Inject
    public LoginPageBean(@Named SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    /**
     * Checks if a user is already logged in and redirects to the home page if true.
     * This prevents logged-in users from accessing the login page.
     *
     * @throws IOException If there is an error during redirect
     */
    public void redirectIfLoggedIn() throws IOException {
        if (sessionUserBean.getUser() != null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }
}
