package com.chatapp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String text;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(nullable = false)
    private String username;
    
    @Column(name = "user_image")
    private String userImage;
    
    @Column(name = "message_type")
    private String messageType = "text"; // text, image
    
    @Column(name = "image_url")
    private String imageUrl;
    
    public Message() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Message(String text, Long userId, String username, String userImage) {
        this.text = text;
        this.userId = userId;
        this.username = username;
        this.userImage = userImage;
        this.messageType = "text";
        this.createdAt = LocalDateTime.now();
    }
    
    public Message(String text, Long userId, String username, String userImage, String messageType, String imageUrl) {
        this.text = text;
        this.userId = userId;
        this.username = username;
        this.userImage = userImage;
        this.messageType = messageType;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserImage() {
        return userImage;
    }
    
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
} 