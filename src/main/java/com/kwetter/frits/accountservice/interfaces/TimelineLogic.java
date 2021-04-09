package com.kwetter.frits.accountservice.interfaces;

import com.kwetter.frits.accountservice.entity.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TimelineLogic {
    void timeLineAccountCreate(User user) throws Exception;
}
