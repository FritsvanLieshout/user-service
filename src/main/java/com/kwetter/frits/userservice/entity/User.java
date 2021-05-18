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

    @Column(length = 24, unique = true)
    @Length(min= 2, max = 24)
    @NotNull
    private String username;

    @Column(length = 36)
    @Length(min= 2, max = 36)
    private String nickName;

    private String profileImage;
    private String role;
    private Boolean verified;
    private String biography;

    public User() {}

    public User(String username, String nickName, String profileImage, String role, boolean verified, String biography) {
        this.username = username;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.role = role;
        this.verified = verified;
        this.biography = biography;
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

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getBiography() { return biography; }

    public void setBiography(String biography) { this.biography = biography; }

    @Override
    public String toString() {
        return "User [id=" + userId + ", username=" + username + ", nickname=" + nickName + ", image=" + profileImage + ", verified=" + verified + "]";
    }
}
