
package com.calidadagua.usuarios.features.users.create;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
import com.calidadagua.usuarios.shared.exception.DuplicateEmailException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreateUserHandlerTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserHandler handler;

    @Test
    void shouldCreateUserSuccessfully() {

        CreateUserRequest request = new CreateUserRequest(
                "Juan",
                "Perez",
                "juan@waterquality.com",
                "Technician123!",
                "70000000",
                Role.TECHNICIAN
        );

        when(repository.existsByEmail(request.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encoded-password");

        when(repository.saveAndFlush(any(User.class)))
                .thenAnswer(invocation -> {

                    User user = invocation.getArgument(0);

                    user.setId(UUID.randomUUID());

                    assertEquals(
                            "encoded-password",
                            user.getPassword()
                    );

                    return user;
                });

        UserResponse response = handler.execute(request);

        assertNotNull(response.id());
        assertEquals("Juan", response.firstName());
        assertEquals(request.email(), response.email());
        assertEquals(Role.TECHNICIAN, response.role());
        assertTrue(response.active());

        verify(repository).saveAndFlush(any(User.class));
    }

    @Test
    void shouldRejectDuplicateEmail() {

        CreateUserRequest request = new CreateUserRequest(
                "Juan",
                "Perez",
                "juan@waterquality.com",
                "Technician123!",
                "70000000",
                Role.TECHNICIAN
        );

        when(repository.existsByEmail(request.email()))
                .thenReturn(true);

        assertThrows(
                DuplicateEmailException.class,
                () -> handler.execute(request)
        );

        verify(repository, never())
                .saveAndFlush(any(User.class));

        verifyNoInteractions(passwordEncoder);
    }
}

