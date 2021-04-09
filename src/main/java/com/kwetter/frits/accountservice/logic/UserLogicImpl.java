package com.kwetter.frits.accountservice.logic;

import com.kwetter.frits.accountservice.entity.User;
import com.kwetter.frits.accountservice.interfaces.UserLogic;
import com.kwetter.frits.accountservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserLogicImpl implements UserLogic {

    private UserRepository userRepository;

    public UserLogicImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User createAccount(User user) {
        return userRepository.save(user);
    }
}
