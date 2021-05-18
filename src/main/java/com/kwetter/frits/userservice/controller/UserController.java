package com.kwetter.frits.userservice.controller;

import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.entity.UserViewModel;
import com.kwetter.frits.userservice.exception.ExceptionMessage;
import com.kwetter.frits.userservice.logic.AuthLogicImpl;
import com.kwetter.frits.userservice.logic.UserLogicImpl;
import com.kwetter.frits.userservice.logic.TimelineLogicImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserLogicImpl userLogic;
    private final AuthLogicImpl authLogic;
    private final TimelineLogicImpl timelineLogic;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserLogicImpl userLogic, AuthLogicImpl authLogic, TimelineLogicImpl timelineLogic) {
        this.userLogic = userLogic;
        this.authLogic = authLogic;
        this.timelineLogic = timelineLogic;
    }

    @GetMapping("/status")
    public ResponseEntity<User> retrieveUserByUsername(@RequestParam String username) {
        try {
            var user = userLogic.findByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserDataByCookie(@CookieValue(name = "Authorization") String token) {
        if (token.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        var user = userLogic.findUserByToken(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createNewUser(@RequestBody UserViewModel user) {
        try {
            if (!userLogic.userAlreadyExist(user.getUsername())) {
                var createdUser = userLogic.createUser(new User(user.getUsername(), user.getNickName(), user.getProfileImage(), "KWETTER_USER", false));
                if (createdUser != null && user.getPassword() != null) {
                    //PASSWORD need to be a base64 string format, this need to be initialized in frontend #Security
                    authLogic.registerNewUser(createdUser.getUserId(), createdUser.getUsername(), user.getPassword());
                    timelineLogic.timeLineUserCreate(createdUser);
                    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
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
