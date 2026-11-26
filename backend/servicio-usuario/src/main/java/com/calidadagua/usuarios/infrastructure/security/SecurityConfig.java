package com.calidadagua.usuarios.infrastructure.security;

import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.UUID;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        userDetailsService
                );

        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserRepository repository) throws Exception {

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
                                "/api/auth/login",
                                "/actuator/health"
                        ).permitAll()

                        .requestMatchers(
                                "/api/auth/profile"
                        ).authenticated()

                        .requestMatchers(
                                "/api/users",
                                "/api/users/**"
                        ).hasRole("ADMIN")

                        .anyRequest().denyAll()
                )

                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(token -> {

                                    UUID id;

                                    try {
                                        id = UUID.fromString(
                                                token.getSubject()
                                        );

                                    } catch (IllegalArgumentException |
                                             NullPointerException ex) {

                                        throw new OAuth2AuthenticationException(
                                                new OAuth2Error("invalid_token")
                                        );
                                    }

                                    User user = repository.findById(id)
                                            .orElseThrow(() ->
                                                    new OAuth2AuthenticationException(
                                                            new OAuth2Error(
                                                                    "invalid_token"
                                                            )
                                                    )
                                            );

                                    if (!user.isActive()) {

                                        throw new OAuth2AuthenticationException(
                                                new OAuth2Error("invalid_token")
                                        );
                                    }

                                    if (!user.getRole().name().equals(
                                            token.getClaimAsString("role"))) {

                                        throw new OAuth2AuthenticationException(
                                                new OAuth2Error("invalid_token")
                                        );
                                    }

                                    return new JwtAuthenticationToken(
                                            token,
                                            List.of(
                                                    new SimpleGrantedAuthority(
                                                            "ROLE_" +
                                                                    user.getRole().name()
                                                    )
                                            )
                                    );
                                })
                        )
                );

        return http.build();
    }
}
