package com.calidadagua.locations.features.communities.update;

import com.calidadagua.locations.domain.model.*;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.CommunityResponse;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCommunityHandler {

    private final CommunityRepository repository;
    private final MunicipalityRepository parentRepository;

    @Transactional
    public CommunityResponse execute(
            UUID id, UpdateCommunityRequest request) {

        Community community = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Community not found"
                        )
                );

        Municipality municipality = parentRepository
                .findById(request.municipalityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Municipality not found"
                        )
                );

        String name = request.name().trim();

        if (repository
                .existsByNameIgnoreCaseAndMunicipality_IdAndIdNot(
                        name, request.municipalityId(), id)) {

            throw new DuplicateResourceException(
                    "Community already exists"
            );
        }

        community.setName(name);
        community.setMunicipality(municipality);

        return CommunityResponse.from(
                repository.saveAndFlush(community)
        );
    }
}