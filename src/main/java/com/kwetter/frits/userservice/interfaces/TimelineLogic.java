package com.kwetter.frits.userservice.interfaces;

import com.kwetter.frits.userservice.entity.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TimelineLogic {
    void timeLineUserCreate(User user) throws Exception;
}
