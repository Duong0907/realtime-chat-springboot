package com.example.demo.controller;

import com.example.demo.dto.websocket.ResponseType;
import com.example.demo.dto.websocket.WebSocketMessageRequest;
import com.example.demo.dto.websocket.WebSocketResponse;
import com.example.demo.service.WebSocketService;
import com.example.demo.utils.JSONUtil;
import com.example.demo.utils.WebSocketUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ChatWebsocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final JSONUtil jsonUtil;
    private final WebSocketUtil webSocketUtil;
    private final WebSocketService webSocketService;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        sessions.add(session);

        WebSocketResponse response = webSocketService.connect(session);
        String jsonResponse = jsonUtil.objectToJSON(response);

        webSocketUtil.broadcast(sessions, jsonResponse);
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        WebSocketMessageRequest request = null;
        try {
            String stringMessage = message.getPayload();
            request = jsonUtil.jsonToWebSocketMessageRequest(stringMessage);
        } catch (Exception e) {
            WebSocketResponse response = WebSocketResponse
                    .builder()
                            .type(ResponseType.ERROR)
                                    .data("Invalid message format")
                                            .build();

            String jsonResponse = jsonUtil.objectToJSON(response);
            webSocketUtil.broadcast(sessions, jsonResponse);
            return;
        }

        WebSocketResponse response = webSocketService.sendMessage(session, request);
        String jsonResponse = jsonUtil.objectToJSON(response);

        webSocketUtil.broadcast(sessions, jsonResponse);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,@NonNull CloseStatus status) throws Exception {
        sessions.remove(session);

        WebSocketResponse response = webSocketService.disconnect(session);
        String jsonResponse = jsonUtil.objectToJSON(response);

        webSocketUtil.broadcast(sessions, jsonResponse);
    }
}


/*
    Basic flow for chat feature:
    - Input: userId, message content, conversation id
    - Output: message (id, content, sender, conversation)
    - When a connection is established:
        + Mark isAvailable of that user to true, broadcast to all other users
    - When the connection is closed:
        + Mark isAvailable of that user to false, update the lastTimeOnline, broadcast to all other users
    - When a user send a text message:
        + Server read message and authenticate the user
        + Create a new Message on database
        + Update LastRead
        + Broadcast the message to other users in that conversation (using sessions)
        + Clients receive message and update ChatItem, if ChatItem is choosing, update currentChat


    When a user clicks on chatItem
        - Input: userId, conversationId
        - Update LastRead of that user in conversation to latest message
        - Broadcast to users in that conversation

    Socket Message format:
    - type: MESSAGE, ONLINE, LAST_SEEN
    - content:
        + Message
        + userId
        + userId, messageId, conversationId
 */