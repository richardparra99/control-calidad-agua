package com.calidadagua.locations.features.departments.create;

import com.calidadagua.locations.domain.model.Department;
import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.shared.exception.DuplicateResourceException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDepartmentHandlerTest {

    @Mock
    private DepartmentRepository repository;

    @InjectMocks
    private CreateDepartmentHandler handler;

    @Test
    void shouldCreateDepartment() {

        when(repository.saveAndFlush(any(Department.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        var response = handler.execute(
                new CreateDepartmentRequest("Santa Cruz")
        );

        assertEquals("Santa Cruz", response.name());

        verify(repository).saveAndFlush(any(Department.class));
    }

    @Test
    void shouldRejectDuplicateDepartment() {

        when(repository.existsByNameIgnoreCase("Santa Cruz"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> handler.execute(
                        new CreateDepartmentRequest("Santa Cruz")
                )
        );

        verify(repository, never()).saveAndFlush(any());
    }
}