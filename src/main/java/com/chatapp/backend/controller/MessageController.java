package com.chatapp.backend.controller;

import com.chatapp.backend.dto.MessageRequest;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    @PostMapping
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageRequest messageRequest,
                                        Authentication authentication) {
        try {
            Long userId = (Long) authentication.getPrincipal();
            Message message = messageService.sendMessage(messageRequest, userId);
            
            // Send message via WebSocket to all connected clients
            messagingTemplate.convertAndSend("/topic/messages", message);
            
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId,
                                          Authentication authentication) {
        try {
            Long userId = (Long) authentication.getPrincipal();
            messageService.deleteMessage(messageId, userId);
            
            // Notify all clients about message deletion via WebSocket
            messagingTemplate.convertAndSend("/topic/messages/deleted", messageId);
            
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 