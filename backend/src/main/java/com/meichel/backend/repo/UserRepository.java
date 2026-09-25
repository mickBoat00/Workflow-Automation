package com.meichel.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meichel.backend.entity.User;

public interface UserRepository  extends  JpaRepository<User, Long>{

    User findByEmail(String email);
    
}
