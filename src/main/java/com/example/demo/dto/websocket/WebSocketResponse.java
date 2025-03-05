package com.example.demo.dto.websocket;

import lombok.Builder;

@Builder
public class WebSocketResponse {
    public ResponseType type;
    public Object data;
}
