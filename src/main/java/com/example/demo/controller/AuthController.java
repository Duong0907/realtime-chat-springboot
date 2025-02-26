package com.example.demo.controller;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.dto.Response;
import com.example.demo.service.AuthService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = authService.login(loginRequest);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<Response> register(@RequestBody RegisterRequest registerRequest) {
        Response response = authService.register(registerRequest);
        return new ResponseEntity<>(response, response.getStatusCode());
    }
}
