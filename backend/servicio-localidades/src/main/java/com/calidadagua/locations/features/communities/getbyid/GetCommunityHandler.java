package com.calidadagua.locations.features.communities.getbyid;

import com.calidadagua.locations.infrastructure.persistence.CommunityRepository;
import com.calidadagua.locations.shared.dto.CommunityResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCommunityHandler {

    private final CommunityRepository repository;

    @Transactional(readOnly = true)
    public CommunityResponse execute(UUID id) {

        return repository.findById(id)
                .map(CommunityResponse::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Community not found"
                        )
                );
    }
}