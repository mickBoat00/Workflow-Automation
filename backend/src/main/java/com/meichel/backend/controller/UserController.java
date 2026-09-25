package com.meichel.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meichel.backend.dto.LoginRequest;
import com.meichel.backend.dto.UserDto;
import com.meichel.backend.entity.User;
import com.meichel.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor 
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/me")
    public List<User> currentUser(){
        return userService.allUsers();
    }


    @PostMapping("/signin")
	public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
		Authentication authenticationResponse =
			authenticationManager.authenticate(authenticationRequest);

        System.out.println("auth response principla ---> " + authenticationResponse.getPrincipal());
		
        return ResponseEntity.ok().body("Nice one");
	}

    @PostMapping ("/signup")
    public UserDetails signUpUser(@RequestBody  UserDto userDto) {
        return userService.signUpUser(userDto);
    }
}
