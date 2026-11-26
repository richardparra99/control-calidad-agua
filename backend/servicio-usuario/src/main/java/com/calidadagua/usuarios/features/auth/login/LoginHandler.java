package com.calidadagua.usuarios.features.auth.login;

import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.infrastructure.security.JwtService;
import com.calidadagua.usuarios.shared.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LoginHandler {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public LoginResponse execute(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (request.password()
                .getBytes(StandardCharsets.UTF_8).length > 72) {

            throw new BadCredentialsException(
                    "Invalid credentials"
            );
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );

        User user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid credentials"
                        )
                );

        if (!user.isActive()) {

            throw new DisabledException(
                    "Invalid credentials"
            );
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer",
                UserResponse.from(user)
        );
    }
}
