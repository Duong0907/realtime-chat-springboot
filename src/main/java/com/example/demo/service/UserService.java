package com.example.demo.service;

import com.example.demo.dto.Response;
import com.example.demo.dto.conversation.ConversationDto;
import com.example.demo.dto.user.UpdateUserDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public Response getCurrentUser() throws CustomException {
        Object rawPrinciple = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        CustomUserDetails principle = (CustomUserDetails) rawPrinciple;
        Long userId = principle.getUserId();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User not found");
        }

        UserDto userDto = new UserDto(user);

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Get current user successfully")
                .data(userDto)
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

    public Response updateUserProfilePicture() {
        return null;
    }

    public Response deleteUserById(Long userId) throws CustomException {
        userRepository.deleteById(userId);

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Delete user by id successfully")
                .build();
    }

    // Find users without common conversations
    public Response getAllNewSuggestedUsers() {
        Object rawPrinciple = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        CustomUserDetails principle = (CustomUserDetails) rawPrinciple;
        Long userId = principle.getUserId();

        String jpql = "SELECT u FROM User u " +
                    "WHERE u.id NOT IN (" +
                    "    SELECT u2.id " +
                    "    FROM User u2 " +
                    "    JOIN u2.conversations c " +
                    "    WHERE c.id IN (" +
                    "        SELECT c2.id " +
                    "        FROM User u3 " +
                    "        JOIN u3.conversations c2 " +
                    "        WHERE u3.id = :userId" +
                    "    )" +
                    ")";

        Query query = entityManager.createQuery(jpql, User.class);
        query.setParameter("userId", userId);
        List<User> users = query.getResultList();
        List<UserDto> userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Get all new suggested users successfully")
                .data(userDtos)
                .build();
    }

    public Response getAllCurrentConversationOfUser() {
        Object rawPrinciple = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        CustomUserDetails principle = (CustomUserDetails) rawPrinciple;
        Long userId = principle.getUserId();

        User currentUser = userRepository.findById(userId).orElse(null);

        if (currentUser == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User not found");
        }

        Set<ConversationDto> conversations = currentUser
                .getConversations()
                .stream()
                .sorted(Comparator.comparing(Conversation::getUpdatedAt).reversed())
                .map(ConversationDto::new)
                .collect(Collectors.toSet());

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Get all current conversation of user successfully")
                .data(new ArrayList<>(conversations))
                .build();
    }
}
