package com.jarno.usersandbox.repositories;

import java.util.Optional;

import com.jarno.usersandbox.models.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
    
    Optional<UserAccount> findByUsername(String username);
}
