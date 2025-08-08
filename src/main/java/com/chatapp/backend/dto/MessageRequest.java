package com.chatapp.backend.dto;

public class MessageRequest {
    private String text;
    
    private String messageType = "text"; // text, image
    
    private String imageUrl;
    
    public MessageRequest() {}
    
    public MessageRequest(String text) {
        this.text = text;
        this.messageType = "text";
    }
    
    public MessageRequest(String text, String messageType, String imageUrl) {
        this.text = text;
        this.messageType = messageType;
        this.imageUrl = imageUrl;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
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