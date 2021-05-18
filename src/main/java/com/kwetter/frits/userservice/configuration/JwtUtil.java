package com.kwetter.frits.userservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtil {

    @Value("${security.jwt.header}")
    private String header;

    @Value("${security.jwt.secret}")
    private String secret;

    public String getHeader() {
        return header;
    }

    public String getSecret() {
        return secret;
    }
}
