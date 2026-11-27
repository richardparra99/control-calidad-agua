package com.calidadagua.locations.features.departments.getbyid;

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
class GetDepartmentHandlerTest {

    @Mock
    private DepartmentRepository repository;

    @InjectMocks
    private GetDepartmentHandler handler;

    @Test
    void shouldReturnDepartment() {

        var department = TestData.department();

        when(repository.findById(department.getId()))
                .thenReturn(Optional.of(department));

        var response = handler.execute(department.getId());

        assertEquals(department.getId(), response.id());
    }

    @Test
    void shouldRejectMissingDepartment() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(id)
        );
    }
}