
package com.calidadagua.usuarios.features.users.update;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
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
class UpdateUserHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UpdateUserHandler handler;

    @Test
    void shouldUpdateUserSuccessfully() {

        UUID id = UUID.randomUUID();
        UUID adminId = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setEmail("juan@waterquality.com");
        user.setRole(Role.TECHNICIAN);

        UpdateUserRequest request = new UpdateUserRequest(
                "Juan Carlos",
                "Perez",
                "juan@waterquality.com",
                "71111111",
                Role.TECHNICIAN
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(user));

        when(repository.saveAndFlush(user))
                .thenReturn(user);

        UserResponse response =
                handler.execute(id, request, adminId);

        assertEquals("Juan Carlos", response.firstName());
        assertEquals("71111111", response.phone());

        verify(repository).saveAndFlush(user);
    }

    @Test
    void shouldRejectUpdateWhenUserDoesNotExist() {

        UUID id = UUID.randomUUID();

        UpdateUserRequest request = new UpdateUserRequest(
                "Juan",
                "Perez",
                "juan@waterquality.com",
                "70000000",
                Role.TECHNICIAN
        );

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> handler.execute(
                        id, request, UUID.randomUUID()
                )
        );

        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    void shouldPreventAdminFromChangingOwnRole() {

        UUID adminId = UUID.randomUUID();

        User admin = new User();
        admin.setId(adminId);
        admin.setEmail("admin@waterquality.com");
        admin.setRole(Role.ADMIN);
        admin.setActive(true);

        UpdateUserRequest request = new UpdateUserRequest(
                "System",
                "Administrator",
                "admin@waterquality.com",
                null,
                Role.TECHNICIAN
        );

        when(repository.findById(adminId))
                .thenReturn(Optional.of(admin));

        assertThrows(
                OperationNotAllowedException.class,
                () -> handler.execute(
                        adminId, request, adminId
                )
        );

        verify(repository, never()).saveAndFlush(any());
    }
}
