package com.calidadagua.locations.features.waterbodies.listbycommunity;

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
class ListWaterBodiesByCommunityHandlerTest {

    @Mock
    private WaterBodyRepository repository;

    @Mock
    private CommunityRepository parentRepository;

    @InjectMocks
    private ListWaterBodiesByCommunityHandler handler;

    @Test
    void shouldReturnWaterBodiesByCommunity() {

        var waterBody = TestData.waterBody();

        UUID id = waterBody.getCommunity().getId();

        when(parentRepository.existsById(id))
                .thenReturn(true);

        when(repository.findByCommunity_Id(id))
                .thenReturn(List.of(waterBody));

        var response = handler.execute(id);

        assertEquals(1, response.size());
        assertEquals(id, response.getFirst().communityId());
    }

    @Test
    void shouldRejectMissingCommunity() {

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