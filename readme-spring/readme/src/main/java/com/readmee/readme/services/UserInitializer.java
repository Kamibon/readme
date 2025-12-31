package com.readmee.readme.services;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.readmee.readme.models.User;
import com.readmee.readme.repositories.UserRepository;

@Component
public class UserInitializer {

    private final UserRepository userRepository;

    public UserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleAuthenticationSuccess(AbstractAuthenticationEvent event) {
        if (event.getAuthentication() instanceof JwtAuthenticationToken token) {
            Jwt jwt = token.getToken();

            String userId = jwt.getSubject(); 
            String username = jwt.getClaim("preferred_username");
            String email = jwt.getClaim("email");

            userRepository.findByUserId(userId).orElseGet(() -> {
                User user = User.builder()
                        .userId(userId)
                        .username(username)
                        .email(email)
                        .favorites(new HashSet<>())
                        .follows(new HashSet<>())
                        .followed(new HashSet<>())
                        .build();
                return userRepository.save(user);
            });
        }
    }
}
