package com.calidadagua.locations.features.departments.update;

import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;
import com.calidadagua.locations.support.TestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDepartmentHandlerTest {

    @Mock
    private DepartmentRepository repository;

    @InjectMocks
    private UpdateDepartmentHandler handler;

    @Test
    void shouldUpdateDepartment() {

        var department = TestData.department();

        when(repository.findById(department.getId()))
                .thenReturn(Optional.of(department));

        when(repository.saveAndFlush(department))
                .thenReturn(department);

        var response = handler.execute(
                department.getId(),
                new UpdateDepartmentRequest("Updated Department")
        );

        assertEquals("Updated Department", response.name());
        verify(repository).saveAndFlush(department);
    }

    @Test
    void shouldRejectMissingDepartment() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(
                        id,
                        new UpdateDepartmentRequest("Santa Cruz")
                )
        );

        verify(repository, never()).saveAndFlush(any());
    }
}