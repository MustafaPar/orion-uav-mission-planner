package com.uavfleet.controller;

import com.uavfleet.dto.AuthRequestDto;
import com.uavfleet.dto.AuthResponseDto;
import com.uavfleet.entity.User;
import com.uavfleet.repository.UserRepository;
import com.uavfleet.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Login and registration (JWT issuance)")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody AuthRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken: " + request.getUsername());
        }

        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), "OPERATOR");
        userRepository.save(user);

        String token = jwtService.generateToken(toUserDetails(user));
        return new AuthResponseDto(token, user.getUsername());
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        String token = jwtService.generateToken(toUserDetails(user));
        return new AuthResponseDto(token, user.getUsername());
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
