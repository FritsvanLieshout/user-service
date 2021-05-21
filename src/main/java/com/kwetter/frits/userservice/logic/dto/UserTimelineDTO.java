package com.kwetter.frits.userservice.logic.dto;

import java.util.UUID;

public class UserTimelineDTO {

    private UUID userId;
    private String username;
    private String nickName;
    private String profileImage;
    private Boolean verified;
    private String biography;

    public UserTimelineDTO() {}

    public UserTimelineDTO(UUID userId, String username, String nickName, String profileImage, Boolean verified, String biography) {
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.verified = verified;
        this.biography = biography;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getBiography() { return biography; }

    public void setBiography(String biography) { this.biography = biography; }
}
