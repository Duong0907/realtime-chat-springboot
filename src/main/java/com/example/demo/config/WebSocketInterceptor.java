package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
        try {
            // Get token from URL parameter
            String query = request.getURI().getQuery();

            if (query == null || !query.contains("token=")) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }

            String token = UriComponentsBuilder.newInstance().query(query)
                    .build()
                    .getQueryParams()
                    .getFirst("token");

            if (token == null) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }

            // Verify token and extract user information
            Claims claims = jwtService.validateToken(token);
            Long userId = claims.get("id", Long.class);
            User user = userRepository.findById(userId).orElse(null);

            // User not found
            if (user == null) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }

            // Store user information in attributes to be accessed in WebSocket handler
            attributes.put("user", user);
            return true;
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler, Exception exception) {
        // Not needed for authentication
    }
}