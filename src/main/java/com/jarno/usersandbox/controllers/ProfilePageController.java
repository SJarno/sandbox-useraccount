package com.jarno.usersandbox.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.jarno.usersandbox.models.UserAccount;
import com.jarno.usersandbox.repositories.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfilePageController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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
    @PostMapping("/register-user")
    public String registerUser(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String role
    ) {
        Optional<UserAccount> existingAccount = userAccountRepository.findByUsername(username);
        if (!existingAccount.isPresent()) {
            UserAccount userAccount = new UserAccount(
            username, passwordEncoder.encode(password), 
            new ArrayList<>(Arrays.asList(role)));
            userAccountRepository.save(userAccount);
        }
        
        return "redirect:/profile";
    }

    /* Create super users */

    /* Update admin account */
    @PostMapping("/update-account")
    public String updateAdminAccount(
        @RequestParam String oldPassword,
        @RequestParam String newPassword
    ) { 
        Optional<UserAccount> userToUpdate = userAccountRepository.findByUsername(getAuthenticatedUser().getName());
        if (passwordEncoder.matches(oldPassword, userToUpdate.get().getPassword())) {
            userToUpdate.get().setPassword(passwordEncoder.encode(newPassword));
            userAccountRepository.save(userToUpdate.get());
        }
        
        return "redirect:/profile";
    }

}
