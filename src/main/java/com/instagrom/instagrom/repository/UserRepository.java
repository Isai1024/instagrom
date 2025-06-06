package com.instagrom.instagrom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagrom.instagrom.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);

}
