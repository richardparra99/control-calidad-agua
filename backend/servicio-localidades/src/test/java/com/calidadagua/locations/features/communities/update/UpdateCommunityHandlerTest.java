package com.calidadagua.locations.features.communities.update;

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
class UpdateCommunityHandlerTest {

    @Mock
    private CommunityRepository repository;

    @Mock
    private MunicipalityRepository parentRepository;

    @InjectMocks
    private UpdateCommunityHandler handler;

    @Test
    void shouldUpdateCommunity() {

        var community = TestData.community();
        var municipality = community.getMunicipality();

        when(repository.findById(community.getId()))
                .thenReturn(Optional.of(community));

        when(parentRepository.findById(municipality.getId()))
                .thenReturn(Optional.of(municipality));

        when(repository.saveAndFlush(community))
                .thenReturn(community);

        var response = handler.execute(
                community.getId(),
                new UpdateCommunityRequest(
                        "Updated Community", municipality.getId()
                )
        );

        assertEquals("Updated Community", response.name());
    }

    @Test
    void shouldRejectMissingCommunity() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(
                        id,
                        new UpdateCommunityRequest(
                                "Test Community", UUID.randomUUID()
                        )
                )
        );

        verifyNoInteractions(parentRepository);
    }
}