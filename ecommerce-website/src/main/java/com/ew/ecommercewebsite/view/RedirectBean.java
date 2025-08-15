package com.ew.ecommercewebsite.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;

/**
 * Bean responsible for handling page redirections in the application.
 * This class is request scoped and handles navigation logic.
 */
@Named
@RequestScoped
public class RedirectBean {

    /**
     * Reference to the session user bean to access user-related information
     */
    private final SessionUserBean sessionUserBean;

    /**
     * Constructs a RedirectBean with the required SessionUserBean dependency
     *
     * @param sessionUserBean the session bean containing user information
     */
    @Inject
    public RedirectBean(SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    /**
     * Redirects to home page if order confirmation conditions are not met.
     * Checks if user is logged in and if order has been confirmed in the session.
     *
     * @throws IOException if redirect operation fails
     */
    public void redirectOrderConfirmation() throws IOException {
        if (sessionUserBean.getUser() == null ||
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("orderConfirmed") == null ||
                ! (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("orderConfirmed")) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
        }
    }

    /**
     * Redirects to the previous page using the HTTP referer header.
     * Falls back to home page if referer is not available.
     *
     * @throws IOException if redirect operation fails
     */
    public void redirectToPreviousPage() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String referer = ec.getRequestHeaderMap().get("referer");

        if (referer != null) {
            ec.redirect(referer);
        } else {
            ec.redirect("home.xhtml"); // Fallback
        }
    }

//    public void redirectProduct() throws IOException{
//        String productId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
//        if (productId == null || productId.isBlank()){
//            FacesContext.getCurrentInstance().getExternalContext()
//                    .redirect("home.xhtml?faces-redirect=true");
//        }
//    }
}
