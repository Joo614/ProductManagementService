package com.assignment.productmanagementservice.auth.config;

import com.assignment.productmanagementservice.auth.token.AuthTokenProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Getter
@ConfigurationProperties(prefix = "app")
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long tokenValidTime;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenValidTime;
    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(secret, tokenValidTime, refreshTokenValidTime);
    }
}
