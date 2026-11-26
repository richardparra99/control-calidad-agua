package com.calidadagua.usuarios.infrastructure.security;

import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = repository
                .findByEmail(
                        email.trim().toLowerCase(Locale.ROOT)
                )
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Invalid credentials"
                        )
                );

        return org.springframework.security.core.userdetails
                .User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .disabled(!user.isActive())
                .build();
    }
}
