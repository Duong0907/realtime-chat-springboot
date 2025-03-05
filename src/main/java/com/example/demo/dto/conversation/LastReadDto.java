package com.example.demo.dto.conversation;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.LastRead;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
public class LastReadDto {
    private Long id;
    private UserDto user;
    private Instant createdAt;
    private Instant updatedAt;
    private MessageDto message;

    public LastReadDto(LastRead lastRead) {
        this.id = lastRead.getId();
        this.user = new UserDto(lastRead.getUser());
        this.createdAt = lastRead.getCreatedAt();
        this.updatedAt = lastRead.getUpdatedAt();
        this.message = new MessageDto(lastRead.getMessage());
    }
}
