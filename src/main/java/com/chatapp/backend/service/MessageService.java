package com.chatapp.backend.service;

import com.chatapp.backend.dto.MessageRequest;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserService userService;
    
    public List<Message> getAllMessages() {
        return messageRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Message sendMessage(MessageRequest messageRequest, Long userId) {
        User user = userService.findById(userId);
        
        Message message = new Message();
        message.setText(messageRequest.getText());
        message.setUserId(userId);
        message.setUsername(user.getUsername());
        message.setUserImage(user.getImageUrl());
        message.setMessageType(messageRequest.getMessageType());
        message.setImageUrl(messageRequest.getImageUrl());
        
        return messageRepository.save(message);
    }
    
    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        
        // Check if the user owns the message
        if (!message.getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own messages");
        }
        
        messageRepository.delete(message);
    }
} 