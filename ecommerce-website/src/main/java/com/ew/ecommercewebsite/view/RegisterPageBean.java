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
 * JSF managed bean for handling registration page logic.
 * Uses request scope to handle single HTTP request/response cycle.
 */
@Named
@RequestScoped
public class RegisterPageBean {

    /**
     * Reference to the session-scoped user bean that maintains user state across the session.
     */
    private final SessionUserBean sessionUserBean;

    /**
     * Constructs a new RegisterPageBean with the provided SessionUserBean.
     *
     * @param sessionUserBean The session-scoped bean containing user information
     */
    @Inject
    public RegisterPageBean(@Named SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    /**
     * Checks if user is already logged in and redirects to home page if true.
     * This prevents logged-in users from accessing the registration page.
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
