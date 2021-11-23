package com.jarno.usersandbox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomLoginController {

    @GetMapping("/custom-login")
    public String loginPage() {
        
        return "custom-login";
    }
    @GetMapping("/success-login")
    public String redirectToIndex(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Logged in succesfully");
        return "redirect:/";
    }
    
    
}
