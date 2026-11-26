
package com.calidadagua.usuarios.features.users.listtechnicians;

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
class ListTechniciansHandlerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ListTechniciansHandler handler;

    @Test
    void shouldReturnActiveTechnicians() {

        User technician = new User();
        technician.setId(UUID.randomUUID());
        technician.setFirstName("Juan");
        technician.setRole(Role.TECHNICIAN);
        technician.setActive(true);

        when(repository.findByRoleAndActiveTrue(
                Role.TECHNICIAN))
                .thenReturn(List.of(technician));

        List<UserResponse> response = handler.execute();

        assertEquals(1, response.size());
        assertEquals(
                Role.TECHNICIAN,
                response.getFirst().role()
        );
        assertTrue(response.getFirst().active());

        verify(repository)
                .findByRoleAndActiveTrue(Role.TECHNICIAN);
    }

    @Test
    void shouldReturnEmptyListWhenNoTechniciansExist() {

        when(repository.findByRoleAndActiveTrue(
                Role.TECHNICIAN))
                .thenReturn(List.of());

        assertTrue(handler.execute().isEmpty());
    }
}
