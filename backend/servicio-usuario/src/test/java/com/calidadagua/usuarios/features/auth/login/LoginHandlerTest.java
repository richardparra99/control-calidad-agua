
package com.calidadagua.usuarios.features.auth.login;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.infrastructure.security.JwtService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginHandlerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository repository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginHandler handler;

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest(
                "admin@waterquality.com",
                "AdminLocal2026!"
        );

        User admin = new User();
        admin.setId(UUID.randomUUID());
        admin.setEmail(request.email());
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        Authentication authentication =
                mock(Authentication.class);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(repository.findByEmail(request.email()))
                .thenReturn(Optional.of(admin));

        when(jwtService.generateToken(admin))
                .thenReturn("test-jwt-token");

        LoginResponse response = handler.execute(request);

        assertEquals("test-jwt-token", response.token());
        assertEquals("Bearer", response.type());
        assertEquals(Role.ADMIN, response.user().role());

        verify(jwtService).generateToken(admin);
    }

    @Test
    void shouldRejectInvalidCredentials() {

        LoginRequest request = new LoginRequest(
                "admin@waterquality.com",
                "WrongPassword"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(
                        new BadCredentialsException(
                                "Invalid credentials"
                        )
                );

        assertThrows(
                BadCredentialsException.class,
                () -> handler.execute(request)
        );

        verifyNoInteractions(repository, jwtService);
    }

    @Test
    void shouldRejectDisabledUser() {

        LoginRequest request = new LoginRequest(
                "juan@waterquality.com",
                "Technician123!"
        );

        when(authenticationManager.authenticate(any()))
                .thenThrow(
                        new DisabledException("Account disabled")
                );

        assertThrows(
                DisabledException.class,
                () -> handler.execute(request)
        );

        verifyNoInteractions(repository, jwtService);
    }
}
