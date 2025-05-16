package com.example.demo.dto.conversation;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private ImageDto image;
    private UserDto sender;
    private Long conversationId;

    private Long tempId;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
        if (message.getImage() != null)
            this.image = new ImageDto(message.getImage());
        this.sender = new UserDto(message.getSender());
        this.conversationId = message.getConversation().getId();
    }
}
