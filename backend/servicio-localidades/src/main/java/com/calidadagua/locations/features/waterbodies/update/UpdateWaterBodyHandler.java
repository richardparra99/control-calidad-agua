package com.calidadagua.locations.features.waterbodies.update;

import com.calidadagua.locations.domain.model.*;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.WaterBodyResponse;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateWaterBodyHandler {

    private final WaterBodyRepository repository;
    private final CommunityRepository parentRepository;

    @Transactional
    public WaterBodyResponse execute(
            UUID id, UpdateWaterBodyRequest request) {

        WaterBody waterBody = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Water body not found"
                        )
                );

        Community community = parentRepository
                .findById(request.communityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Community not found"
                        )
                );

        String name = request.name().trim();

        if (repository
                .existsByNameIgnoreCaseAndCommunity_IdAndIdNot(
                        name, request.communityId(), id)) {

            throw new DuplicateResourceException(
                    "Water body already exists"
            );
        }

        waterBody.setName(name);
        waterBody.setType(request.type());
        waterBody.setDescription(request.description());
        waterBody.setLatitude(request.latitude());
        waterBody.setLongitude(request.longitude());
        waterBody.setCommunity(community);

        return WaterBodyResponse.from(
                repository.saveAndFlush(waterBody)
        );
    }
}