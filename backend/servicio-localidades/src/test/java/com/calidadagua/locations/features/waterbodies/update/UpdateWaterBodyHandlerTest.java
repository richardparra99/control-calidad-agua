package com.calidadagua.locations.features.waterbodies.update;

import com.calidadagua.locations.domain.model.WaterBodyType;
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
class UpdateWaterBodyHandlerTest {

    @Mock
    private WaterBodyRepository repository;

    @Mock
    private CommunityRepository parentRepository;

    @InjectMocks
    private UpdateWaterBodyHandler handler;

    @Test
    void shouldUpdateWaterBody() {

        var waterBody = TestData.waterBody();
        var community = waterBody.getCommunity();

        when(repository.findById(waterBody.getId()))
                .thenReturn(Optional.of(waterBody));

        when(parentRepository.findById(community.getId()))
                .thenReturn(Optional.of(community));

        when(repository.saveAndFlush(waterBody))
                .thenReturn(waterBody);

        var request = new UpdateWaterBodyRequest(
                "Updated River",
                WaterBodyType.RIVER,
                "Updated monitoring site",
                -18.20,
                -63.90,
                community.getId()
        );

        var response = handler.execute(
                waterBody.getId(), request
        );

        assertEquals("Updated River", response.name());
        assertEquals(-18.20, response.latitude(), 0.000001);
    }

    @Test
    void shouldRejectMissingWaterBody() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        var request = new UpdateWaterBodyRequest(
                "Test River",
                WaterBodyType.RIVER,
                "Monitoring site",
                -18.18,
                -63.87,
                UUID.randomUUID()
        );

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(id, request)
        );

        verifyNoInteractions(parentRepository);
    }
}