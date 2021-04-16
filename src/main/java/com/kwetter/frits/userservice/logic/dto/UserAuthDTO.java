package com.kwetter.frits.userservice.logic.dto;

import java.util.UUID;

public class UserAuthDTO {

    private UUID userId;
    private String username;
    private String password;

    public UserAuthDTO(UUID userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UUID getUserId() { return userId; }

    public void setUserId(UUID userId) { this.userId = userId; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
