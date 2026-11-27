package com.calidadagua.locations.features.communities.list;

import com.calidadagua.locations.infrastructure.persistence.CommunityRepository;
import com.calidadagua.locations.support.TestData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListCommunitiesHandlerTest {

    @Mock
    private CommunityRepository repository;

    @InjectMocks
    private ListCommunitiesHandler handler;

    @Test
    void shouldReturnCommunities() {

        when(repository.findAll())
                .thenReturn(List.of(TestData.community()));

        var response = handler.execute();

        assertEquals(1, response.size());
        assertEquals("Test Community", response.getFirst().name());
    }

    @Test
    void shouldReturnEmptyList() {

        when(repository.findAll()).thenReturn(List.of());

        assertTrue(handler.execute().isEmpty());
    }
}