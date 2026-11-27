package com.calidadagua.locations.features.municipalities.create;

import com.calidadagua.locations.domain.model.Municipality;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.exception.*;
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
class CreateMunicipalityHandlerTest {

    @Mock
    private MunicipalityRepository repository;

    @Mock
    private DepartmentRepository parentRepository;

    @InjectMocks
    private CreateMunicipalityHandler handler;

    @Test
    void shouldCreateMunicipality() {

        var department = TestData.department();

        when(parentRepository.findById(department.getId()))
                .thenReturn(Optional.of(department));

        when(repository.saveAndFlush(any(Municipality.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        var response = handler.execute(
                new CreateMunicipalityRequest(
                        "Samaipata", department.getId()
                )
        );

        assertEquals("Samaipata", response.name());
        assertEquals(department.getId(), response.departmentId());
    }

    @Test
    void shouldRejectMissingDepartment() {

        UUID id = UUID.randomUUID();

        when(parentRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(
                        new CreateMunicipalityRequest("Samaipata", id)
                )
        );

        verify(repository, never()).saveAndFlush(any());
    }
}