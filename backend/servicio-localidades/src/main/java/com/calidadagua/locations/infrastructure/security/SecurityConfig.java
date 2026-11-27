package com.calidadagua.locations.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;

import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserServiceClient userServiceClient) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/actuator/health"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.GET, "/api/**"
                        ).hasAnyRole("ADMIN", "TECHNICIAN")

                        .requestMatchers(
                                HttpMethod.POST, "/api/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT, "/api/**"
                        ).hasRole("ADMIN")

                        .anyRequest().denyAll()
                )

                .oauth2ResourceServer(oauth -> oauth

                        .authenticationEntryPoint(
                                (request, response, exception) -> {

                                    if (exception instanceof
                                            OAuth2AuthenticationException error
                                            && "temporarily_unavailable".equals(
                                            error.getError().getErrorCode())) {

                                        response.sendError(
                                                503,
                                                "User service is unavailable"
                                        );

                                    } else {

                                        new BearerTokenAuthenticationEntryPoint()
                                                .commence(
                                                        request,
                                                        response,
                                                        exception
                                                );
                                    }
                                })

                        .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                token -> {

                                    UserServiceClient.UserProfile profile =
                                            userServiceClient.getProfile(
                                                    token.getTokenValue()
                                            );

                                    UUID subject;

                                    try {

                                        subject = UUID.fromString(
                                                token.getSubject()
                                        );

                                    } catch (IllegalArgumentException |
                                             NullPointerException ex) {

                                        throw invalidToken();
                                    }

                                    if (!subject.equals(profile.id())
                                            || profile.role() == null
                                            || !profile.role().equals(
                                            token.getClaimAsString("role"))
                                            || !List.of("ADMIN", "TECHNICIAN")
                                            .contains(profile.role())) {

                                        throw invalidToken();
                                    }

                                    return new JwtAuthenticationToken(
                                            token,
                                            List.of(
                                                    new SimpleGrantedAuthority(
                                                            "ROLE_" + profile.role()
                                                    )
                                            )
                                    );
                                }))
                );

        return http.build();
    }

    private OAuth2AuthenticationException invalidToken() {

        return new OAuth2AuthenticationException(
                new OAuth2Error("invalid_token")
        );
    }
}