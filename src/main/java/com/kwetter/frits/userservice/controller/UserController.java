package com.kwetter.frits.userservice.controller;

import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.entity.UserViewModel;
import com.kwetter.frits.userservice.logic.AuthLogicImpl;
import com.kwetter.frits.userservice.logic.UserLogicImpl;
import com.kwetter.frits.userservice.logic.TimelineLogicImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                var userId = userLogic.generateUserId();
                var createdUser = userLogic.createUser(new User(userId, user.getUsername(), user.getNickName(), user.getProfileImage(), "KWETTER_USER", false, user.getBiography()));
                if (createdUser != null && user.getPassword() != null) {
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

    @PutMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody UserViewModel user) {
        try {
            var userObject = new User(user.getUserId(), user.getUsername(), user.getNickName(), user.getProfileImage(), user.getRole(), user.getVerified(), user.getBiography());
            var updatedUser = userLogic.editUser(userObject);

            if (updatedUser != null) {
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/permanent/remove")
    public ResponseEntity<Boolean> removeUser(@RequestBody UserViewModel user) {
        try {
            var userObject = userLogic.findByUsername(user.getUsername());
            userLogic.removeUser(userObject);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
