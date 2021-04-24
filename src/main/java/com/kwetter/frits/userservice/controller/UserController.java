package com.kwetter.frits.userservice.controller;

import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.entity.UserViewModel;
import com.kwetter.frits.userservice.exception.ExceptionMessage;
import com.kwetter.frits.userservice.logic.AuthLogicImpl;
import com.kwetter.frits.userservice.logic.UserLogicImpl;
import com.kwetter.frits.userservice.logic.TimelineLogicImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserLogicImpl userLogic;
    private final AuthLogicImpl authLogic;
    private final TimelineLogicImpl timelineLogic;

    public UserController(UserLogicImpl userLogic, AuthLogicImpl authLogic, TimelineLogicImpl timelineLogic) {
        this.userLogic = userLogic;
        this.authLogic = authLogic;
        this.timelineLogic = timelineLogic;
    }

    @GetMapping("/status")
    public ResponseEntity<User> retrieveUserById(@RequestBody User user) {
        try {
            User _user = userLogic.findByUsername(user.getUsername());
            if (_user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(_user, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createNewUser(@RequestBody UserViewModel user) {
        try {
            if (!userLogic.userAlreadyExist(user.getUsername())) {
                User _user = userLogic.createUser(new User(user.getUsername(), user.getNickName(), user.getProfileImage(), false));
                if (_user != null && user.getPassword() != null) {
                    //PASSWORD need to be a base64 string format, this need to be initialized in frontend #Security
                    authLogic.registerNewUser(_user.getUserId(), _user.getUsername(), user.getPassword());
                    timelineLogic.timeLineUserCreate(_user);
                    return new ResponseEntity<>(_user, HttpStatus.CREATED);
                }
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
