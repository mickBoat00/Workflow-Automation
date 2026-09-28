package com.meichel.backend.utils;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.meichel.backend.entity.User;

import io.jsonwebtoken.Jwts;

@Service
public class JwtUtils {

    @Value("${jwt.secret}") 
    private String secret;

    public String generateToken(User user) {
        byte[] bytes = Base64.getDecoder().decode(secret);
        SecretKey secretKey= new SecretKeySpec(bytes, "HmacSHA256");

        return Jwts.builder()
        .subject(String.valueOf(user.getId()))
        .claim("email", user.getEmail())
        .claim("planSlug", user.getPlanSlug())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
        .signWith(secretKey)
        .compact();
    }
}
