package com.calidadagua.locations.features.waterbodies.create;

import com.calidadagua.locations.domain.model.*;
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
class CreateWaterBodyHandlerTest {

    @Mock
    private WaterBodyRepository repository;

    @Mock
    private CommunityRepository parentRepository;

    @InjectMocks
    private CreateWaterBodyHandler handler;

    @Test
    void shouldCreateWaterBody() {

        var community = TestData.community();

        when(parentRepository.findById(community.getId()))
                .thenReturn(Optional.of(community));

        when(repository.saveAndFlush(any(WaterBody.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        var request = new CreateWaterBodyRequest(
                "Test River",
                WaterBodyType.RIVER,
                "Monitoring site",
                -18.18,
                -63.87,
                community.getId()
        );

        var response = handler.execute(request);

        assertEquals("Test River", response.name());
        assertEquals(WaterBodyType.RIVER, response.type());
        assertEquals(community.getId(), response.communityId());
        assertEquals(-18.18, response.latitude(), 0.000001);
    }

    @Test
    void shouldRejectMissingCommunity() {

        UUID id = UUID.randomUUID();

        when(parentRepository.findById(id))
                .thenReturn(Optional.empty());

        var request = new CreateWaterBodyRequest(
                "Test River",
                WaterBodyType.RIVER,
                "Monitoring site",
                -18.18,
                -63.87,
                id
        );

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(request)
        );

        verify(repository, never()).saveAndFlush(any());
    }
}