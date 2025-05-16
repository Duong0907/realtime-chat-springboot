package com.example.demo.filter;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.Response;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtAuthenticationToken;
import com.example.demo.security.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        // 1. Decide whether we want to apply the filter

        // 2. Check credentials and [authenticate | reject]
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            handleJwtException(response, HttpStatus.UNAUTHORIZED, "JWT token not found");
            return;
        }

        final String jwt = authHeader.substring(7);

        Claims claims = jwtService.validateToken(jwt);
        if (claims == null || jwtService.isTokenExpired(jwt)) {
            handleJwtException(response, HttpStatus.UNAUTHORIZED, "You must provide a valid token");
            return;
        }

        long userId = claims.get("id", Integer.class);
        User user = userRepository.findById(userId).orElse(null);
        UserDetails userDetails = new CustomUserDetails(user);

        // Create and save new authentication token
        Authentication jwtAuthenticationToken = new JwtAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContext newContext = SecurityContextHolder.getContext();
        newContext.setAuthentication(jwtAuthenticationToken);
        SecurityContextHolder.setContext(newContext);


        // (*) Persisting the session of authentication
        // SecurityContextRepository securityContextRepository;
        // securityContextRepository.saveContext(newContext, request, response);

        // 3. Call next
        filterChain.doFilter(request, response);

        // 4. No cleaning
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Declare no-need-checking-jwt routes here
        return request.getServletPath().startsWith("/auth") || request.getServletPath().startsWith("/websocket");
    }

    private void handleJwtException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        // Create an error response object
        Response errorResponse = Response.builder().statusCode(status).message(message).build();

        response.setStatus(status.value());
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
