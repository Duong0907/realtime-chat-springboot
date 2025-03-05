package com.example.demo.dto.websocket;

import com.example.demo.dto.user.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WebSocketConnectionData {
    private UserDto user;
}
