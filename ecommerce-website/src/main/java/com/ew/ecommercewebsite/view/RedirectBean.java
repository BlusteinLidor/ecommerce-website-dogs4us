package com.ew.ecommercewebsite.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;

@Named
@RequestScoped
public class RedirectBean {

    private final SessionUserBean sessionUserBean;

    @Inject
    public RedirectBean(SessionUserBean sessionUserBean) {
        this.sessionUserBean = sessionUserBean;
    }

    public void redirectOrderConfirmation() throws IOException {
        if (sessionUserBean.getUser() == null ||
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("orderConfirmed") == null ||
                ! (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("orderConfirmed")) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("home.xhtml?faces-redirect=true");
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
