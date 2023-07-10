package com.assignment.productmanagementservice.grobal.config;

import com.assignment.productmanagementservice.auth.config.JwtConfig;
import com.assignment.productmanagementservice.auth.filter.CustomFilterConfigurer;
import com.assignment.productmanagementservice.auth.handler.MemberAccessDeniedHandler;
import com.assignment.productmanagementservice.auth.handler.MemberAuthenticationEntryPoint;
import com.assignment.productmanagementservice.auth.service.RefreshService;
import com.assignment.productmanagementservice.auth.token.AuthTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final CustomFilterConfigurer customFilterConfigurer;
    private final AuthTokenProvider tokenProvider;
    private final JwtConfig jwtConfig;
    private final RefreshService refreshService;

    public SecurityConfig(CustomFilterConfigurer customFilterConfigurer,
                          AuthTokenProvider tokenProvider, JwtConfig jwtConfig, RefreshService refreshService) {
        this.customFilterConfigurer = customFilterConfigurer;
        this.tokenProvider = tokenProvider;
        this.jwtConfig = jwtConfig;
        this.refreshService = refreshService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .cors().and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(customFilterConfigurer)
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and().authorizeRequests(
                        authorize -> authorize
                                // product
                                .antMatchers(HttpMethod.POST, "/api/v1/products").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PATCH, "/api/v1/products/**").hasRole("ADMIN")
                                .antMatchers("/api/v1/products/**").permitAll()
                                // basic
                                .antMatchers("/docs/index.html").permitAll()
                                .antMatchers("/h2/**").permitAll()
                                .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new MemberAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MemberAccessDeniedHandler();
    }
}
