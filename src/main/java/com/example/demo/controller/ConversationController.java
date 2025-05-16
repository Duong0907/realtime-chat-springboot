package com.example.demo.controller;

import com.example.demo.dto.Response;
import com.example.demo.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversations")
public class    ConversationController {
    private final ConversationService conversationService;

    @GetMapping("/lastreads/{conversation_id}")
    public ResponseEntity<Response> getLastReadStatusOfConversation(@PathVariable("conversation_id") Long conversationId) {
        Response response = conversationService.getLastReadStatusOfConversation(conversationId);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @GetMapping("/{conversation_id}")
    public ResponseEntity<Response> getConversationById(@PathVariable("conversation_id") Long conversationId) {
        Response response = conversationService.getConversationById(conversationId);
        return new ResponseEntity<>(response, response.getStatusCode());
    }
}
