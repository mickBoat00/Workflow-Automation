package com.meichel.backend.exception;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.meichel.backend.dto.response.ApiResponse;

@RestControllerAdvice 
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationError(
        MethodArgumentNotValidException ex) {

        int statusCode = HttpStatus.BAD_REQUEST.value();

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toList();

        var response = new ApiResponse<Void>(
                "Validation failed",
                statusCode,
                null,
                errors,
                Instant.now()
        );

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }

    @ExceptionHandler(DuplicateUserEmailException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEmailError(DuplicateUserEmailException ex) {
        var errors = List.of(ex.getMessage());
        int statusCode = HttpStatus.CONFLICT.value();

        var response = new ApiResponse<Void>(
                "User sign up failed",
                statusCode,
                null,
                errors,
                Instant.now()
        );

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }
}
