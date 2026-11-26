
package com.calidadagua.usuarios.features.auth.profile;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
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
class ProfileHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ProfileHandler handler;

    @Test
    void shouldReturnUserProfile() {

        UUID id = UUID.randomUUID();

        User user = new User();
        user.setId(id);
        user.setFirstName("Juan");
        user.setEmail("juan@waterquality.com");
        user.setRole(Role.TECHNICIAN);
        user.setActive(true);

        when(repository.findById(id))
                .thenReturn(Optional.of(user));

        UserResponse response = handler.execute(id);

        assertEquals(id, response.id());
        assertEquals(user.getEmail(), response.email());
        assertEquals(Role.TECHNICIAN, response.role());
        assertTrue(response.active());
    }

    @Test
    void shouldRejectMissingUserProfile() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> handler.execute(id)
        );
    }
}
