package com.kwetter.frits.userservice.interfaces;

import com.kwetter.frits.userservice.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Validated
public interface UserLogic {
    Optional<User> findById(UUID id);
    User createUser(User user);
    Boolean userAlreadyExist(String username);
    User findByUsername(String username);
    User findUserByToken(String token);
    User editUser(@NotNull(message = "User cannot be null") @Valid User user);
    Boolean removeUser(User user);
    UUID generateUserId();
}
