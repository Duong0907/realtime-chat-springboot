package com.example.demo.utils;

import com.example.demo.entity.Conversation;
import com.example.demo.entity.User;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketUtil {
    private final ConversationRepository conversationRepository;

    public void broadcast(List<WebSocketSession> sessions, String jsonResponse) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(jsonResponse));
            }
        }
    }

    public void broadcastToConversation(List<WebSocketSession> sessions, String jsonResponse, Long conversationId) throws Exception {
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        Map<Long, Long> userMap = new HashMap<>();
        for (User user : conversation.getUsers()) {
            userMap.put(user.getId(), user.getId());
        }

        for (WebSocketSession webSocketSession : sessions) {
            User user = (User) webSocketSession.getAttributes().get("user");
            if (webSocketSession.isOpen() && userMap.containsKey(user.getId())) {
                webSocketSession.sendMessage(new TextMessage(jsonResponse));
            }
        }
    }

    public void sendMessageToUser(WebSocketSession session, String jsonResponse) throws Exception {
        session.sendMessage(new TextMessage(jsonResponse));
    }
}
