package com.calidadagua.locations.features.departments.list;

import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.support.TestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListDepartmentsHandlerTest {

    @Mock
    private DepartmentRepository repository;

    @InjectMocks
    private ListDepartmentsHandler handler;

    @Test
    void shouldReturnDepartments() {

        when(repository.findAll())
                .thenReturn(List.of(TestData.department()));

        var response = handler.execute();

        assertEquals(1, response.size());
        assertEquals("Santa Cruz", response.getFirst().name());
    }

    @Test
    void shouldReturnEmptyList() {

        when(repository.findAll()).thenReturn(List.of());

        assertTrue(handler.execute().isEmpty());
    }
}