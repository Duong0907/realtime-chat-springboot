package com.example.demo.config;

import com.example.demo.controller.ChatWebsocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatWebsocketHandler myWebSocketHandler;
    private final WebSocketInterceptor myInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler, "/websocket")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }
}