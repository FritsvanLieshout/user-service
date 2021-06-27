package com.kwetter.frits.userservice.interfaces;

import com.kwetter.frits.userservice.entity.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModerationLogic {
    void moderationUserCreate(User user);
    void moderationUserEdit(User user);
    void moderationUserDelete(User user);
}
