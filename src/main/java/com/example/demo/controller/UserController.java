package com.example.demo.controller;

import com.example.demo.dto.Response;
import com.example.demo.dto.user.UpdateUserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{user_id}")
    public ResponseEntity<Response> getUserById(@PathVariable("user_id") Long userId) throws Exception  {
        Response response = userService.getUserById(userId);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllUsers() throws Exception  {
        Response response = userService.getAllUsers();
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("/me")
    public ResponseEntity<Response> getCurrentUser() throws Exception {
        Response response = userService.getCurrentUser();
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<Response> updateUserById(@PathVariable("user_id") Long userId, @RequestBody UpdateUserDto updateUserDto) throws Exception {
        Response response = userService.updateUserById(userId, updateUserDto);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @PatchMapping("/profilePicture")
    public ResponseEntity<Response> updateUserProfilePicture() {
        Response response = userService.updateUserProfilePicture();
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Response> deleteUserById(@PathVariable("user_id") Long userId) throws Exception  {
        Response response = userService.deleteUserById(userId);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("/suggestion")
    public ResponseEntity<Response> getAllSuggestedChatUser() {
        Response response = userService.getAllNewSuggestedUsers();
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("/conversation-list")
    public ResponseEntity<Response> getConversationList() {
        Response response = userService.getAllCurrentConversationOfUser();
        return new ResponseEntity<>(response, response.getStatusCode());
    }
}
