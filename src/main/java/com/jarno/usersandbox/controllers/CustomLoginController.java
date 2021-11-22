package com.jarno.usersandbox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomLoginController {

    @GetMapping("/custom-login")
    public String loginPage() {
        return "custom-login";
    }
    
    
}
