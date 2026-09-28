package com.meichel.backend.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meichel.backend.dto.request.SignUpRequest;
import com.meichel.backend.dto.response.ApiResponse;
import com.meichel.backend.dto.response.TokenResponse;
import com.meichel.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController  
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor 
public class UserController {

    private final UserService userService;
    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<TokenResponse>> signUpUsers(@Valid  @RequestBody SignUpRequest signUpRequest){
        TokenResponse tokenResponse = userService.createUser(signUpRequest);
        var body = new ApiResponse<>(
                "User created",
                HttpStatus.CREATED.value(),
                tokenResponse,
                List.of(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
    
}
