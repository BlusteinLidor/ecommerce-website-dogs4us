package com.ew.ecommercewebsite.view;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class HomeBean {
    private String message = "Welcome to Dogs4Us";

    public String getMessage() {
        return message;
    }


}
