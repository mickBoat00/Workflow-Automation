package com.meichel.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meichel.backend.entity.User;

public interface UserRepository extends JpaRepository {
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
    
}
