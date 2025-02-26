package com.example.demo.service;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.RegisterRequest;
import com.example.demo.dto.Response;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public Response register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)    // Register for normal users
                .build();

        User savedUser = this.userRepository.save(user);
        savedUser.setPassword(null);

        UserDto userDto = new UserDto(savedUser);

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Register successfully")
                .data(userDto)
                .build();
    }

    public Response login(LoginRequest loginRequest) {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);

            return Response
                    .builder()
                    .statusCode(HttpStatus.OK)
                    .message("Login successfully")
                    .data(jwt)
                    .build();
        } catch (AuthenticationException authException) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Wrong username or password");
        }
    }
}
