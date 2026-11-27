package com.calidadagua.locations.features.municipalities.listbydepartment;

import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;
import com.calidadagua.locations.support.TestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMunicipalitiesByDepartmentHandlerTest {

    @Mock
    private MunicipalityRepository repository;

    @Mock
    private DepartmentRepository parentRepository;

    @InjectMocks
    private ListMunicipalitiesByDepartmentHandler handler;

    @Test
    void shouldReturnMunicipalitiesByDepartment() {

        var municipality = TestData.municipality();
        UUID id = municipality.getDepartment().getId();

        when(parentRepository.existsById(id))
                .thenReturn(true);

        when(repository.findByDepartment_Id(id))
                .thenReturn(List.of(municipality));

        var response = handler.execute(id);

        assertEquals(1, response.size());
        assertEquals(id, response.getFirst().departmentId());
    }

    @Test
    void shouldRejectMissingDepartment() {

        UUID id = UUID.randomUUID();

        when(parentRepository.existsById(id))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(id)
        );

        verifyNoInteractions(repository);
    }
}