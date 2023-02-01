package com.example.sampleapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizer -> authorizer
            .requestMatchers("/api/v1/students").authenticated()
            .requestMatchers("/api/v1/persons/*").authenticated()
            .requestMatchers("/home", "/", "index").denyAll()
            .anyRequest().permitAll()
        );

        return http.build();
    }
}
