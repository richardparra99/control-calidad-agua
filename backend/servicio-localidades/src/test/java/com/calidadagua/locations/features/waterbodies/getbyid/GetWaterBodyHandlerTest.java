package com.calidadagua.locations.features.waterbodies.getbyid;

import com.calidadagua.locations.infrastructure.persistence.WaterBodyRepository;
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
class GetWaterBodyHandlerTest {

    @Mock
    private WaterBodyRepository repository;

    @InjectMocks
    private GetWaterBodyHandler handler;

    @Test
    void shouldReturnWaterBody() {

        var waterBody = TestData.waterBody();

        when(repository.findById(waterBody.getId()))
                .thenReturn(Optional.of(waterBody));

        var response = handler.execute(waterBody.getId());

        assertEquals(waterBody.getId(), response.id());
        assertEquals(waterBody.getType(), response.type());
    }

    @Test
    void shouldRejectMissingWaterBody() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(id)
        );
    }
}