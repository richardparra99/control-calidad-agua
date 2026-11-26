
package com.calidadagua.usuarios.features.users.deactivate;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.exception.*;

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
class DeactivateUserHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private DeactivateUserHandler handler;

    @Test
    void shouldDeactivateTechnicianSuccessfully() {

        UUID id = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setRole(Role.TECHNICIAN);
        user.setActive(true);

        when(repository.findById(id))
                .thenReturn(Optional.of(user));

        handler.execute(id, adminId);

        assertFalse(user.isActive());

        verify(repository).save(user);
    }

    @Test
    void shouldPreventAdminFromDeactivatingOwnAccount() {

        UUID adminId = UUID.randomUUID();

        assertThrows(
                OperationNotAllowedException.class,
                () -> handler.execute(adminId, adminId)
        );

        verifyNoInteractions(repository);
    }

    @Test
    void shouldPreventDeactivationOfLastActiveAdmin() {

        UUID id = UUID.randomUUID();

        User admin = new User();
        admin.setId(id);
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        when(repository.findById(id))
                .thenReturn(Optional.of(admin));

        when(repository.countByRoleAndActiveTrue(Role.ADMIN))
                .thenReturn(1L);

        assertThrows(
                OperationNotAllowedException.class,
                () -> handler.execute(
                        id, UUID.randomUUID()
                )
        );

        assertTrue(admin.isActive());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldRejectDeactivationOfMissingUser() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> handler.execute(
                        id, UUID.randomUUID()
                )
        );

        verify(repository, never()).save(any());
    }
}
