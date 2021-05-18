package com.kwetter.frits.userservice.logic;

import com.kwetter.frits.userservice.configuration.JwtUtil;
import com.kwetter.frits.userservice.entity.User;
import com.kwetter.frits.userservice.interfaces.UserLogic;
import com.kwetter.frits.userservice.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserLogicImpl implements UserLogic {

    private UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserLogicImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
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
}
