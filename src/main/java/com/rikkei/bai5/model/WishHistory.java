package com.rikkei.bai5.model;

import java.time.LocalDateTime;

public class WishHistory {
    private String id;
    private String wishType;
    private String status;
    private String message;
    private LocalDateTime createdAt;

    public WishHistory() {}

    public WishHistory(String id, String wishType, String status, String message, LocalDateTime createdAt) {
        this.id = id;
        this.wishType = wishType;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getWishType() { return wishType; }
    public void setWishType(String wishType) { this.wishType = wishType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
