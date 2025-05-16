package com.example.demo.service;

import com.example.demo.dto.conversation.MessageDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.websocket.ResponseType;
import com.example.demo.dto.websocket.WebSocketMessageRequest;
import com.example.demo.dto.websocket.WebSocketResponse;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.WebSocketUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;
import java.util.Objects;

@Service
@AllArgsConstructor
public class WebSocketService {
    private final UserRepository userRepository;
    private final WebSocketUtil webSocketUtil;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public WebSocketResponse connect(WebSocketSession session) throws Exception {
        User user = (User) session.getAttributes().get("user");
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
        User user = (User) session.getAttributes().get("user");

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

    public WebSocketResponse createMessage(WebSocketSession session, WebSocketMessageRequest request) throws Exception {
        User user = (User) session.getAttributes().get("user");

        long conversationId = request.getMessage().getConversationId();
        long tempId = request.getMessage().getTempId();

        System.out.println("Temp id: " + tempId);

        // Check if user is in the conversation
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        boolean isInConversation = false;

        for (User userInConversation : conversation.getUsers()) {
            if (userInConversation.getId().equals(user.getId())) {
                isInConversation = true;
                break;
            }
        }

        if (!isInConversation) {
            return WebSocketResponse
                    .builder()
                    .type(ResponseType.ERROR)
                    .data("User is not in the conversation")
                    .build();
        }

        // Create message and add it to conversation
        Message message = Message
                .builder()
                .content(request.getMessage().getContent())
                .sender(user)
                .conversation(conversation)
                .build();

        Message createdMessage = messageRepository.save(message);
        MessageDto messageDto = new MessageDto(createdMessage);
        messageDto.setTempId(tempId);

        return WebSocketResponse
                .builder()
                .type(ResponseType.MESSAGE)
                .data(messageDto)
                .build();
    }
}
