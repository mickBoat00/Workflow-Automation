package com.meichel.backend.exception;

public class DuplicateUserEmailException extends RuntimeException  {

    public DuplicateUserEmailException(String message) {
        super(message);
    }
    
}
