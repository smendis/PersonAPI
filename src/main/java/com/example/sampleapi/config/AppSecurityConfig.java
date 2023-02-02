package com.example.sampleapi.config;

import com.example.sampleapi.security.ApplicationUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizer -> authorizer
            .requestMatchers("/api/v1/students/**").hasRole(ApplicationUserRoles.STUDENT.name())
            .requestMatchers("/api/v1/courses/**").hasRole(ApplicationUserRoles.ADMIN.name())
            .requestMatchers("/api/v1/persons/**").authenticated()
            .requestMatchers("/home", "/", "index").denyAll()
            .anyRequest().permitAll()
        ).httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails smendisUser = User.builder()
                .username("smendis")
                .password(this.passwordEncoder.encode("password"))
                .roles(ApplicationUserRoles.ADMIN.name())
                .build();
        UserDetails nvimansaUser = User.builder()
                .username("nvimansa")
                .password(this.passwordEncoder.encode("password"))
                .roles(ApplicationUserRoles.STUDENT.name())
                .build();
        UserDetails smalinsaUser = User.builder()
                .username("smalinsa")
                .password(this.passwordEncoder.encode("password"))
                .roles(ApplicationUserRoles.STUDENT.name())
                .build();
        return new InMemoryUserDetailsManager(smendisUser, nvimansaUser, smalinsaUser);
    }

}
