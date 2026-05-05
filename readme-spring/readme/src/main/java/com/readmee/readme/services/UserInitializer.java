package com.readmee.readme.services;
import java.util.HashSet;

import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.readmee.readme.models.User;
import com.readmee.readme.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserInitializer {

    private final UserRepository userRepository;

    public User getOrCreateUser(Jwt jwt) {
    String userId = jwt.getSubject();

    return userRepository.findByUserId(userId)
        .orElseGet(() -> {
            try {
                User user = User.builder()
                        .userId(userId)
                        .username(jwt.getClaim("preferred_username"))
                        .email(jwt.getClaim("email"))
                        .favorites(new HashSet<>())
                        .build();

                return userRepository.save(user);

            } catch (DataIntegrityViolationException e) {
                return userRepository.findByUserId(userId).orElseThrow();
            }
        });
}

   @EventListener
public void handleAuthenticationSuccess(AbstractAuthenticationEvent event) {
    if (event.getAuthentication() instanceof JwtAuthenticationToken token) {
        Jwt jwt = token.getToken();

        String userId = jwt.getSubject();
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");

        userRepository.findByUserId(userId).orElseGet(() -> {
            try {
                User user = User.builder()
                        .userId(userId)
                        .username(username)
                        .email(email)
                        .favorites(new HashSet<>())
                        .build();

                return userRepository.save(user);

            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                return userRepository.findByUserId(userId).orElseThrow();
            }
        });
    }
}
}
