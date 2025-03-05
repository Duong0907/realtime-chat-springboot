package com.example.demo.service;

import com.example.demo.dto.Response;
import com.example.demo.dto.conversation.LastReadDto;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.LastRead;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.LastReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final LastReadRepository lastReadRepository;

    public Response getLastReadStatusOfConversation(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        if (conversation == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Conversation not found");
        }

        List<LastRead> lastReads = lastReadRepository.findLastReadsByConversationId(conversationId);
        List<LastReadDto> lastReadDtos = lastReads.stream().map(LastReadDto::new).toList();

        return Response
                .builder()
                .statusCode(HttpStatus.OK)
                .message("Get last read status successfully")
                .data(lastReadDtos)
                .build();
    }
}
