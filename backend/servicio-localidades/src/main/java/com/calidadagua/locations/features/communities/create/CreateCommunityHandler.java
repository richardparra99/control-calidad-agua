package com.calidadagua.locations.features.communities.create;

import com.calidadagua.locations.domain.model.*;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.CommunityResponse;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCommunityHandler {

    private final CommunityRepository repository;
    private final MunicipalityRepository parentRepository;

    @Transactional
    public CommunityResponse execute(
            CreateCommunityRequest request) {

        Municipality municipality = parentRepository
                .findById(request.municipalityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Municipality not found"
                        )
                );

        String name = request.name().trim();

        if (repository.existsByNameIgnoreCaseAndMunicipality_Id(
                name, request.municipalityId())) {

            throw new DuplicateResourceException(
                    "Community already exists"
            );
        }

        Community community = new Community();

        community.setName(name);
        community.setMunicipality(municipality);

        return CommunityResponse.from(
                repository.saveAndFlush(community)
        );
    }
}