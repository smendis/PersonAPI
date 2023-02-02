package com.example.sampleapi.config;

import com.example.sampleapi.security.ApplicationUserPermissions;
import com.example.sampleapi.security.ApplicationUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
        http
            .csrf().disable()
            .cors().disable()
            .authorizeHttpRequests(authorizer -> authorizer
                .requestMatchers("/api/v1/students/**").hasAnyRole(ApplicationUserRoles.ADMIN.name(), ApplicationUserRoles.ADMINTRAINEE.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAuthority(ApplicationUserPermissions.COURSE_WRITE.getPermissionName())
                .requestMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAuthority(ApplicationUserPermissions.COURSE_WRITE.getPermissionName())
                .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAuthority(ApplicationUserPermissions.COURSE_WRITE.getPermissionName())
                .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").hasAuthority(ApplicationUserPermissions.COURSE_READ.getPermissionName())
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
//                .roles(ApplicationUserRoles.ADMIN.name())
                .authorities(ApplicationUserRoles.ADMIN.getAuthorities())
                .build();
        UserDetails nvimansaUser = User.builder()
                .username("nvimansa")
                .password(this.passwordEncoder.encode("password"))
//                .roles(ApplicationUserRoles.ADMINTRAINEE.name())
                .authorities(ApplicationUserRoles.ADMINTRAINEE.getAuthorities())
                .build();
        UserDetails smalinsaUser = User.builder()
                .username("smalinsa")
                .password(this.passwordEncoder.encode("password"))
                .roles(ApplicationUserRoles.STUDENT.name())
                .build();
        return new InMemoryUserDetailsManager(smendisUser, nvimansaUser, smalinsaUser);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
