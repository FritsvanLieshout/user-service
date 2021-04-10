package com.kwetter.frits.userservice.entity;

import java.util.UUID;

public class UserViewModel {

    private UUID userId;
    private String username;
    private String password;
    private String nickName;
    private String profileImage;
    private Boolean verified;

    public UserViewModel() {}

    public UserViewModel(String username, String password, String nickName, String profileImage, boolean verified) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.verified = verified;
    }

    public UserViewModel(UUID userId, String username, String password, String nickName, String profileImage, boolean verified) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.verified = verified;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImage() { return profileImage; }

    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

}
