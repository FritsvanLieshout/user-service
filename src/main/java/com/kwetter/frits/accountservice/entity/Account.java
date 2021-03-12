package com.kwetter.frits.accountservice.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 24)
    @Length(min= 2, max = 24)
    private String username;

    @Column(length = 36)
    @Length(min= 2, max = 36)
    private String name;

    @Column(length = 48)
    @Length(min= 2, max = 48)
    private String surname;

    private Boolean verified;

    public Account() {}

    public Account(String username, String name, String surname, boolean verified) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.verified = verified;
    }

    public Account(Integer id, String username, String name, String surname, boolean verified) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.verified = verified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", username=" + username + ", name=" + name + ", surname=" + surname + "]";
    }
}
