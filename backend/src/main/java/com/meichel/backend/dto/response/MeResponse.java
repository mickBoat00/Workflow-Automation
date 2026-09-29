package com.meichel.backend.dto.response;

public record MeResponse(
        Long id,
        String fullName,
        String email,
        String planSlug
) {
}
