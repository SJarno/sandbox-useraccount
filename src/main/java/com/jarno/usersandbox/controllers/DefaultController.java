package com.jarno.usersandbox.controllers;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.jarno.usersandbox.models.UserAccount;
import com.jarno.usersandbox.repositories.UserAccountRepository;
import com.jarno.usersandbox.security.CustomUserDetailsService;
import com.jarno.usersandbox.security.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* private CustomUserDetailsService userDetailsService;

    private SecurityConfiguration securityConfiguration; */

    @GetMapping("/")
    public String index() {
        //securityConfiguration.authProvider().afterPropertiesSet();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println();
        System.out.println(auth.getDetails());
        System.out.println(auth.getPrincipal());
        System.out.println();
        return "index";
    }
    @PostConstruct
    public void init() {
        userAccountRepository.deleteAll();
        UserAccount userAccount = new UserAccount("aa", passwordEncoder.encode("bb"), "rooli");
        userAccountRepository.save(userAccount);
    }
    
}
