package com.meichel.backend.dto.response;

import java.time.Instant;
import java.util.List;

public record ApiResponse<T>(
    String message, 
    int status,
    T data,
    List<String> errors,
    Instant timestamp
) {
    
}
