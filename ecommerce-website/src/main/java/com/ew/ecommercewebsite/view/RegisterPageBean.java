package com.ew.ecommercewebsite.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class RegisterPageBean {

    private final SessionUserBean sessionUserBean;

    @Inject
    public RegisterPageBean(@Named SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    public void redirectIfLoggedIn() throws IOException {
        if (sessionUserBean.getUser() != null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }
}
