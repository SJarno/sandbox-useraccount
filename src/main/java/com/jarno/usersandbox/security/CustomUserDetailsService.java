package com.jarno.usersandbox.security;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jarno.usersandbox.models.UserAccount;
import com.jarno.usersandbox.repositories.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> optionalAccount = userAccountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                optionalAccount.get().getUsername(), 
                optionalAccount.get().getPassword(), 
                true, 
                true, 
                true, 
                true, 
                optionalAccount.get().getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList())
            );
        }
        throw new UsernameNotFoundException("No such user: "+username);
    }
    
}
