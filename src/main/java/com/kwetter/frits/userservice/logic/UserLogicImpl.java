package com.kwetter.frits.userservice.logic;

import com.kwetter.frits.userservice.configuration.JwtUtil;
import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.interfaces.UserLogic;
import com.kwetter.frits.userservice.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserLogicImpl implements UserLogic {

    private final UserRepository userRepository;
    private final TimelineLogicImpl timelineLogic;
    private final ModerationLogicImpl moderationLogic;
    private final JwtUtil jwtUtil;

    public UserLogicImpl(UserRepository userRepository, TimelineLogicImpl timelineLogic, ModerationLogicImpl moderationLogic, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.timelineLogic = timelineLogic;
        this.moderationLogic = moderationLogic;
        this.jwtUtil = jwtUtil;
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
        if (userRepository.existsUserByUsername(username)) return true;
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByToken(String token) {
        var username = getSubjectOfToken(token);
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User editUser(@NotNull(message = "User cannot be null") @Valid User user) {
        var editedUser = userRepository.findUserByUsername(user.getUsername());
        if (editedUser != null) {
            editedUser.setNickName(user.getNickName());
            editedUser.setProfileImage(user.getProfileImage());
            editedUser.setBiography(user.getBiography());
            editedUser.setVerified(user.getVerified());
            editedUser.setRole(user.getRole());
            timelineLogic.timeLineUserEdit(editedUser);
            moderationLogic.moderationUserEdit(editedUser);
            return userRepository.save(editedUser);
        }
        return null;
    }

    @Override
    public Boolean removeUser(User user) {
        if (user != null) {
            timelineLogic.timeLineUserDelete(user);
            timelineLogic.timeLineUserPermanentDelete(user);
            moderationLogic.moderationUserDelete(user);
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public UUID generateUserId() {
        return generateRandomUUID();
    }

    private String getSubjectOfToken(String token) {
        try {
            var signatureAlgorithm = SignatureAlgorithm.HS256;
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtUtil.getSecret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token);

            var body = claimsJws.getBody();
            return body.getSubject();
        }
        catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
    }

    private UUID generateRandomUUID() {
        var uuid = UUID.randomUUID();
        if (userRepository.existsUserByUserId(uuid)) {
            return generateRandomUUID();
        }
        return uuid;
    }
}
