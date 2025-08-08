package com.chatapp.backend.service;

import com.chatapp.backend.dto.AuthResponse;
import com.chatapp.backend.dto.LoginRequest;
import com.chatapp.backend.dto.SignupRequest;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    public AuthResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        User user = userOptional.get();
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        String token = jwtService.generateToken(user.getId(), user.getUsername());
        
        return new AuthResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getImageUrl());
    }
    
    public AuthResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUsername(signupRequest.getUsername());
        user.setImageUrl(signupRequest.getImageUrl());
        
        User savedUser = userRepository.save(user);
        
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getUsername());
        
        return new AuthResponse(token, savedUser.getId(), savedUser.getUsername(), 
                               savedUser.getEmail(), savedUser.getImageUrl());
    }
    
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
} 