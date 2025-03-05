package com.example.demo.dto.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class WebSocketMessageRequest {
    ResponseType type;
    WebSocketMessage message;
}

@Getter
@Setter
class WebSocketMessage {
    private String message;
    private String senderId;
    private String conversationId;
}
