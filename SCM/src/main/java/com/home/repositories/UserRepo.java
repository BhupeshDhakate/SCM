package com.home.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.entities.User;

public interface UserRepo extends JpaRepository<User,String>{
    //extra methods for db related operations
    //custom query methods of custom finder methods
    
    Optional<User>findByEmail(String email);
    Optional<User>findByEmailAndPassword(String email, String password);
    Optional<User>findByEmailToken(String id);
}
