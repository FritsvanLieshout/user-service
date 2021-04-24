package com.kwetter.frits.userservice.logic;

import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.interfaces.UserLogic;
import com.kwetter.frits.userservice.repository.UserRepository;
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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean userAlreadyExist(String username) {
        if (userRepository.existsUserByUsername(username)) {
           return true;
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
