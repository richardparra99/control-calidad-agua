
package com.calidadagua.usuarios.features.users.reactivate;

import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.exception.UserNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReactivateUserHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ReactivateUserHandler handler;

    @Test
    void shouldReactivateUserSuccessfully() {

        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setActive(false);

        when(repository.findById(id))
                .thenReturn(Optional.of(user));

        handler.execute(id);

        assertTrue(user.isActive());

        verify(repository).save(user);
    }

    @Test
    void shouldRejectReactivationOfMissingUser() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> handler.execute(id)
        );

        verify(repository, never()).save(any());
    }
}
