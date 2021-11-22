package com.jarno.usersandbox.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import com.jarno.usersandbox.models.UserAccount;
import com.jarno.usersandbox.repositories.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/")
    public String index() {
        
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
        UserAccount userAccount = new UserAccount("aa", passwordEncoder.encode("bb"), new ArrayList<>(Arrays.asList("ROLE_ADMIN")));
        userAccountRepository.save(userAccount);
    }
    
}
