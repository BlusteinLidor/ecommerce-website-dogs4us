package com.ew.ecommercewebsite.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling URL redirections in the application.
 * This controller manages the redirection of the root URL to the home page.
 */
@Controller
public class RedirectController {

    /**
     * Redirects the root URL ("/") to the home page.
     *
     * @return A string indicating the redirect destination ("redirect:/home.xhtml")
     */
    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/home.xhtml";
    }
}
