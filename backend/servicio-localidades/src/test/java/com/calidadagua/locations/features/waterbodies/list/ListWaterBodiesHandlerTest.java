package com.calidadagua.locations.features.waterbodies.list;

import com.calidadagua.locations.infrastructure.persistence.WaterBodyRepository;
import com.calidadagua.locations.support.TestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListWaterBodiesHandlerTest {

    @Mock
    private WaterBodyRepository repository;

    @InjectMocks
    private ListWaterBodiesHandler handler;

    @Test
    void shouldReturnWaterBodies() {

        when(repository.findAll())
                .thenReturn(List.of(TestData.waterBody()));

        var response = handler.execute();

        assertEquals(1, response.size());
        assertEquals("Test River", response.getFirst().name());
    }

    @Test
    void shouldReturnEmptyList() {

        when(repository.findAll()).thenReturn(List.of());

        assertTrue(handler.execute().isEmpty());
    }
}