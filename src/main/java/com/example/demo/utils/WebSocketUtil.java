package com.example.demo.utils;

import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class WebSocketUtil {
    public CustomUserDetails getUserDetailsFromSession(WebSocketSession session) {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) session.getPrincipal();
        CustomUserDetails principle = (CustomUserDetails) authenticationToken.getPrincipal();
        return principle;
    }

    public void broadcast(List<WebSocketSession> sessions, String jsonResponse) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(jsonResponse));
            }
        }
    }
}
