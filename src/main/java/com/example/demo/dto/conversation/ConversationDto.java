package com.example.demo.dto.conversation;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.Conversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ConversationDto {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private List<UserDto> members;
    private List<MessageDto> messages;

    public ConversationDto(Conversation conversation) {
        this.id = conversation.getId();
        this.name = conversation.getName();
        this.createdAt = conversation.getCreatedAt();
        this.updatedAt = conversation.getUpdatedAt();
        this.members = conversation.getUsers().stream().map(UserDto::new).toList();
        this.messages = conversation.getMessages().stream().map(MessageDto::new).toList();
    }
}
