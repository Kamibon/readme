package com.readmee.readme;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;
    private final CustomAuthEntryPoint customAuthEntryPoint;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(STATELESS)
            )
            .authorizeHttpRequests(auth ->
                auth.anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                oauth2
                    .jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthConverter)
                    )
                    .authenticationEntryPoint(customAuthEntryPoint)
            );

        return http.build();
    }
}

