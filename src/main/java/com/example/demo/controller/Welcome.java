package com.example.demo.controller;

import com.example.demo.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @GetMapping("/welcome")
    public ResponseEntity<Response> welcome() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Response response = Response.builder()
                .statusCode(HttpStatus.OK)
                .message("Welcome to Spring Boot, " + username + "!")
                .build();

        return new ResponseEntity<>(response, response.getStatusCode());
    }
}
