package com.meichel.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
    @NotBlank(message = "Full name cannot be empty.") 
    String fullName, 

    @Email(message = "Invalid email format.")
    @NotBlank (message = "Email cannot be empty.") 
    String email, 

    @NotBlank(message = "Password cannot be empty.")
    @Size(min=8, message = "Password cannot be less than 8 characters")
    String password
) {
    
}
