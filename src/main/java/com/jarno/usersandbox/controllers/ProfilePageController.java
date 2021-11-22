package com.jarno.usersandbox.controllers;

import java.util.Optional;

import com.jarno.usersandbox.models.UserAccount;
import com.jarno.usersandbox.repositories.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilePageController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        if (getAuthenticatedUser().isAuthenticated()) {
            Optional<UserAccount> user = userAccountRepository.findByUsername(
                getAuthenticatedUser().getName());
            model.addAttribute("user", user.get());
        }

        return "profile-page";
    }

    private Authentication getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth;
    }
    /* Create users */

    /* Create super users */

}
