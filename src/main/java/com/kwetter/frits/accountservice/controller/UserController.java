package com.kwetter.frits.accountservice.controller;

import com.kwetter.frits.accountservice.entity.User;
import com.kwetter.frits.accountservice.logic.UserLogicImpl;
import com.kwetter.frits.accountservice.logic.TimelineLogicImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class UserController {

    private final UserLogicImpl accountLogic;
    private final TimelineLogicImpl timelineLogic;

    public UserController(UserLogicImpl accountLogic, TimelineLogicImpl timelineLogic) {
        this.accountLogic = accountLogic;
        this.timelineLogic = timelineLogic;
    }

    @GetMapping()
    public ResponseEntity<User> retrieveAccountById(@RequestBody User user) {
        try {
            Optional<User> _account = accountLogic.findById(user.getUserId());
            if (_account.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            User _userEntity = _account.get();
            return new ResponseEntity<>(_userEntity, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<User> createAccount(@RequestBody User user) {
        try {
            User _user = accountLogic.createAccount(new User(user.getUsername(), user.getNickName(), user.getProfileImage(), false));
            if (_user != null) {
                timelineLogic.timeLineAccountCreate(_user);
                return new ResponseEntity<>(_user, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
