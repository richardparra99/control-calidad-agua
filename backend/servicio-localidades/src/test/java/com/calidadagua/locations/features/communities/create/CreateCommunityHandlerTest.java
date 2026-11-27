package com.calidadagua.locations.features.communities.create;

import com.calidadagua.locations.domain.model.Community;
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
class CreateCommunityHandlerTest {

    @Mock
    private CommunityRepository repository;

    @Mock
    private MunicipalityRepository parentRepository;

    @InjectMocks
    private CreateCommunityHandler handler;

    @Test
    void shouldCreateCommunity() {

        var municipality = TestData.municipality();

        when(parentRepository.findById(municipality.getId()))
                .thenReturn(Optional.of(municipality));

        when(repository.saveAndFlush(any(Community.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        var response = handler.execute(
                new CreateCommunityRequest(
                        "Test Community", municipality.getId()
                )
        );

        assertEquals("Test Community", response.name());
        assertEquals(municipality.getId(), response.municipalityId());
    }

    @Test
    void shouldRejectMissingMunicipality() {

        UUID id = UUID.randomUUID();

        when(parentRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> handler.execute(
                        new CreateCommunityRequest("Test Community", id)
                )
        );

        verify(repository, never()).saveAndFlush(any());
    }
}