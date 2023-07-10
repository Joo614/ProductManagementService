package com.assignment.productmanagementservice.auth.filter;

import com.assignment.productmanagementservice.auth.handler.CustomAuthenticationFailureHandler;
import com.assignment.productmanagementservice.auth.handler.CustomAuthenticationSuccessHandler;
import com.assignment.productmanagementservice.auth.service.RefreshService;
import com.assignment.productmanagementservice.auth.token.AuthTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
    private final AuthTokenProvider authTokenProvider;
    private final RefreshService refreshService;

    public CustomFilterConfigurer(AuthTokenProvider authTokenProvider, RefreshService refreshService) {
        this.authTokenProvider = authTokenProvider;
        this.refreshService = refreshService;
    }

    // 로그인 필터 구현
    @Override
    public void configure(HttpSecurity builder)  {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authTokenProvider, authenticationManager, refreshService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(authTokenProvider);
        builder.addFilter(jwtAuthenticationFilter)
                 .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
