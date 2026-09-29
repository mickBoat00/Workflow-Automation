package com.meichel.backend.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meichel.backend.dto.response.ApiResponse;
import com.meichel.backend.dto.response.MeResponse;
import com.meichel.backend.entity.User;

@RestController
@RequestMapping("/api/v1/me")
public class MeController {

    @GetMapping
    public ResponseEntity<ApiResponse<MeResponse>> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        MeResponse data = new MeResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPlanSlug());
        ApiResponse<MeResponse> body = new ApiResponse<MeResponse>(
                "Current user",
                HttpStatus.OK.value(),
                data,
                List.of(),
                Instant.now());
        return ResponseEntity.ok(body);
    }
}
