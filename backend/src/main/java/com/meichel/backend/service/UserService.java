package com.meichel.backend.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meichel.backend.dto.UserDto;
import com.meichel.backend.entity.User;
import com.meichel.backend.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public UserDetails signUpUser(UserDto userDto) {

        User user = User.builder()
            .fullName(userDto.fullName())
            .email(userDto.email())
            .password(passwordEncoder.encode(userDto.password()))
            .planSlug("free")
            .build();


        return userRepository.save(user);
    }
    
}
