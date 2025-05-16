package com.example.demo.dto.websocket;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WebSocketResponse {
    public ResponseType type;
    public Object data;
}
