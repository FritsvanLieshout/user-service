package com.kwetter.frits.userservice.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(length = 24)
    @Length(min= 2, max = 24)
    @NotNull
    private String username;

    @Column(length = 36)
    @Length(min= 2, max = 36)
    private String nickName;

    private String profileImage;

    private Boolean verified;

    public User() {}

    public User(String username, String nickName, String profileImage, boolean verified) {
        this.username = username;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.verified = verified;
    }

    public User(UUID userId, String username, String nickName, String profileImage, boolean verified) {
        this.userId = userId;
        this.username = username;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() { return profileImage; }

    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "User [id=" + userId + ", username=" + username + ", nickname=" + nickName + ", image=" + profileImage + ", verified=" + verified + "]";
    }
}
