package com.calidadagua.locations.features.municipalities.list;

import com.calidadagua.locations.infrastructure.persistence.MunicipalityRepository;
import com.calidadagua.locations.support.TestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListMunicipalitiesHandlerTest {

    @Mock
    private MunicipalityRepository repository;

    @InjectMocks
    private ListMunicipalitiesHandler handler;

    @Test
    void shouldReturnMunicipalities() {

        when(repository.findAll())
                .thenReturn(List.of(TestData.municipality()));

        var response = handler.execute();

        assertEquals(1, response.size());
        assertEquals("Samaipata", response.getFirst().name());
    }

    @Test
    void shouldReturnEmptyList() {

        when(repository.findAll()).thenReturn(List.of());

        assertTrue(handler.execute().isEmpty());
    }
}