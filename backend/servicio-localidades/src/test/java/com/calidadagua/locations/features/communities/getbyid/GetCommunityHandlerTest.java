package com.calidadagua.locations.features.communities.getbyid;

import com.calidadagua.locations.infrastructure.persistence.CommunityRepository;
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
class GetCommunityHandlerTest {

    @Mock
    private CommunityRepository repository;

    @InjectMocks
    private GetCommunityHandler handler;

    @Test
    void shouldReturnCommunity() {

        var community = TestData.community();

        when(repository.findById(community.getId()))
                .thenReturn(Optional.of(community));

        assertEquals(
                community.getId(),
                handler.execute(community.getId()).id()
        );
    }

    @Test
    void shouldRejectMissingCommunity() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(id)
        );
    }
}