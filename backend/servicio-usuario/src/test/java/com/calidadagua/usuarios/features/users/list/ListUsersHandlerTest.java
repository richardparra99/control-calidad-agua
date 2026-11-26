
package com.calidadagua.usuarios.features.users.list;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUsersHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ListUsersHandler handler;

    @Test
    void shouldReturnAllUsers() {

        User admin = new User();
        admin.setId(UUID.randomUUID());
        admin.setFirstName("System");
        admin.setRole(Role.ADMIN);

        User technician = new User();
        technician.setId(UUID.randomUUID());
        technician.setFirstName("Juan");
        technician.setRole(Role.TECHNICIAN);

        when(repository.findAll())
                .thenReturn(List.of(admin, technician));

        List<UserResponse> response = handler.execute();

        assertEquals(2, response.size());
        assertEquals(Role.ADMIN, response.get(0).role());
        assertEquals(Role.TECHNICIAN, response.get(1).role());

        verify(repository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {

        when(repository.findAll()).thenReturn(List.of());

        List<UserResponse> response = handler.execute();

        assertTrue(response.isEmpty());
    }
}
