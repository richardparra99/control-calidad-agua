package com.calidadagua.locations.features.municipalities.update;

import com.calidadagua.locations.infrastructure.persistence.*;
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
class UpdateMunicipalityHandlerTest {

    @Mock
    private MunicipalityRepository repository;

    @Mock
    private DepartmentRepository parentRepository;

    @InjectMocks
    private UpdateMunicipalityHandler handler;

    @Test
    void shouldUpdateMunicipality() {

        var municipality = TestData.municipality();
        var department = municipality.getDepartment();

        when(repository.findById(municipality.getId()))
                .thenReturn(Optional.of(municipality));

        when(parentRepository.findById(department.getId()))
                .thenReturn(Optional.of(department));

        when(repository.saveAndFlush(municipality))
                .thenReturn(municipality);

        var response = handler.execute(
                municipality.getId(),
                new UpdateMunicipalityRequest(
                        "Updated Municipality", department.getId()
                )
        );

        assertEquals("Updated Municipality", response.name());
    }

    @Test
    void shouldRejectMissingMunicipality() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(
                        id,
                        new UpdateMunicipalityRequest(
                                "Samaipata", UUID.randomUUID()
                        )
                )
        );

        verifyNoInteractions(parentRepository);
    }
}