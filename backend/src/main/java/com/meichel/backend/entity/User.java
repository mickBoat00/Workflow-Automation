package com.meichel.backend.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="users")
@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor 
public class User implements UserDetails {
    @Id 
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String planSlug;
    private String password;

    @CreationTimestamp 
    private LocalDateTime createdAt;
    @UpdateTimestamp 
    private LocalDateTime updatedAt;

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override 
    public String getUsername() {
        return email;
    }

}
