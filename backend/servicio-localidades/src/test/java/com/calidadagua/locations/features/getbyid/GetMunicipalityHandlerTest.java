package com.calidadagua.locations.features.municipalities.getbyid;

import com.calidadagua.locations.infrastructure.persistence.MunicipalityRepository;
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
class GetMunicipalityHandlerTest {

    @Mock
    private MunicipalityRepository repository;

    @InjectMocks
    private GetMunicipalityHandler handler;

    @Test
    void shouldReturnMunicipality() {

        var municipality = TestData.municipality();

        when(repository.findById(municipality.getId()))
                .thenReturn(Optional.of(municipality));

        assertEquals(
                municipality.getId(),
                handler.execute(municipality.getId()).id()
        );
    }

    @Test
    void shouldRejectMissingMunicipality() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(id)
        );
    }
}