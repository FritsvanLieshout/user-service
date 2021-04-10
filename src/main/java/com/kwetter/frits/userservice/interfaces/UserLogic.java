package com.kwetter.frits.userservice.interfaces;

import com.kwetter.frits.userservice.entity.User;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Validated
public interface UserLogic {
    Optional<User> findById(UUID id);
    User createUser(User user);
}
