package com.example.demo.dto.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class WebSocketMessage {
    private String content;

    @SerializedName("conversation_id")
    private Long conversationId;

    // For tracking message on UI
    @SerializedName("temp_id")
    private Long tempId;
}
