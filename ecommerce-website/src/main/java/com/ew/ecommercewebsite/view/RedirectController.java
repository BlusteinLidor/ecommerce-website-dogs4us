package com.ew.ecommercewebsite.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/home.xhtml";
    }
}
