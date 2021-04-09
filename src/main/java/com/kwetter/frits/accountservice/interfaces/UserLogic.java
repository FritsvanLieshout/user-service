package com.kwetter.frits.accountservice.interfaces;

import com.kwetter.frits.accountservice.entity.User;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Validated
public interface UserLogic {
    Optional<User> findById(UUID id);
    User createAccount(User user);
}
