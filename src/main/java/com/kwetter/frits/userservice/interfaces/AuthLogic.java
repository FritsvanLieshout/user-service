package com.kwetter.frits.userservice.interfaces;

import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface AuthLogic {
    void registerNewUser(UUID userId, String username, String password) throws Exception;
}
