package com.meichel.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="users")
public class User {
    @Id 
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String password;

    @CreationTimestamp 
    private LocalDateTime createdat;
    @UpdateTimestamp 
    private LocalDateTime updatedat;


}
