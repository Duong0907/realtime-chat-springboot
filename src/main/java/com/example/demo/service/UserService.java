package com.example.demo.service;

import com.example.demo.dto.Response;
import com.example.demo.dto.user.UpdateUserDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Response getUserById(Long userId) throws CustomException {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User not found");
        }

        UserDto userDto = new UserDto(user);

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Get user by id successfully")
                .data(userDto)
                .build();
    }

    public Response getAllUsers() throws CustomException {
        List<UserDto> users = userRepository
                .findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "User not found");
        }

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Get all users successfully")
                .data(users)
                .build();
    }

    public Response updateUserById(Long userId, UpdateUserDto updateUserDto) throws CustomException {
        User foundUser = userRepository.findById(userId).orElse(null);

        if (foundUser == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Update user with each attribute
        if (updateUserDto.getEmail() != null) {
            User user = userRepository.findByEmail(updateUserDto.getEmail()).orElse(null);
            if (user != null) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists");
            }

            foundUser.setEmail(updateUserDto.getEmail());
        }

        if (updateUserDto.getUsername() != null) {
            User user = userRepository.findByUsername(updateUserDto.getUsername()).orElse(null);
            if (user != null) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists");
            }

            foundUser.setUsername(updateUserDto.getUsername());
        }

        User updatedUser = userRepository.save(foundUser);

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Update user by id successfully")
                .data(updatedUser)
                .build();
    }

    public Response deleteUserById(Long userId) throws CustomException {
        userRepository.deleteById(userId);

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Delete user by id successfully")
                .build();
    }
}
