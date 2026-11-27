package com.calidadagua.locations.features.communities.listbymunicipality;

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
class ListCommunitiesByMunicipalityHandlerTest {

    @Mock
    private CommunityRepository repository;

    @Mock
    private MunicipalityRepository parentRepository;

    @InjectMocks
    private ListCommunitiesByMunicipalityHandler handler;

    @Test
    void shouldReturnCommunitiesByMunicipality() {

        var community = TestData.community();

        UUID id = community.getMunicipality().getId();

        when(parentRepository.existsById(id))
                .thenReturn(true);

        when(repository.findByMunicipality_Id(id))
                .thenReturn(List.of(community));

        var response = handler.execute(id);

        assertEquals(1, response.size());
        assertEquals(id, response.getFirst().municipalityId());
    }

    @Test
    void shouldRejectMissingMunicipality() {

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