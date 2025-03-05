package com.example.demo.service;

import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.websocket.ResponseType;
import com.example.demo.dto.websocket.WebSocketMessageRequest;
import com.example.demo.dto.websocket.WebSocketResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.utils.WebSocketUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final UserRepository userRepository;
    private final WebSocketUtil webSocketUtil;

    public WebSocketResponse connect(WebSocketSession session) throws Exception {
        CustomUserDetails principle = webSocketUtil.getUserDetailsFromSession(session);
        String username = principle.getUsername();

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Update user is available here
        user.setIsAvailable(true);
        User savedUser = userRepository.save(user);
        UserDto userDto = new UserDto(savedUser);

        // Broadcast message to all connected clients
        return WebSocketResponse
                .builder()
                .type(ResponseType.CONNECTION)
                .data(userDto)
                .build();
    }

    public WebSocketResponse disconnect(WebSocketSession session) throws Exception {
        CustomUserDetails principle = webSocketUtil.getUserDetailsFromSession(session);
        String username = principle.getUsername();

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Update user is offline here
        user.setIsAvailable(false);
        user.setLastOnline(Instant.now());
        User savedUser = userRepository.save(user);
        UserDto userDto = new UserDto(savedUser);

        // Broadcast message to all connected clients
        return WebSocketResponse
                .builder()
                .type(ResponseType.DISCONNECTION)
                .data(userDto)
                .build();
    }

    public WebSocketResponse sendMessage(WebSocketSession session, WebSocketMessageRequest request) throws Exception {
        CustomUserDetails principle = webSocketUtil.getUserDetailsFromSession(session);

        return WebSocketResponse
                .builder()
                .type(ResponseType.MESSAGE)
                .data(request)
                .build();
    }
}
