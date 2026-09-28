package com.meichel.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meichel.backend.dto.request.SignUpRequest;
import com.meichel.backend.dto.response.TokenResponse;
import com.meichel.backend.entity.User;
import com.meichel.backend.exception.DuplicateUserEmailException;
import com.meichel.backend.repository.UserRepository;
import com.meichel.backend.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public TokenResponse createUser(SignUpRequest signUpRequest) {

        if(userRepository.existsByEmail(signUpRequest.email())) {
            throw new DuplicateUserEmailException("Email already exist.");
        }

        User user = User
        .builder()
        .fullName(signUpRequest.fullName())
        .email(signUpRequest.email())
        .password(passwordEncoder.encode(signUpRequest.password()))
        .planSlug("free")
        .build();

        User savedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(savedUser);
        return new TokenResponse(token);
    }
    
}
