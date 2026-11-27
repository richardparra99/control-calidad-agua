package com.calidadagua.locations.features.waterbodies.create;

import com.calidadagua.locations.domain.model.*;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.WaterBodyResponse;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateWaterBodyHandler {

    private final WaterBodyRepository repository;
    private final CommunityRepository parentRepository;

    @Transactional
    public WaterBodyResponse execute(
            CreateWaterBodyRequest request) {

        Community community = parentRepository
                .findById(request.communityId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Community not found"
                        )
                );

        String name = request.name().trim();

        if (repository.existsByNameIgnoreCaseAndCommunity_Id(
                name, request.communityId())) {

            throw new DuplicateResourceException(
                    "Water body already exists"
            );
        }

        WaterBody waterBody = new WaterBody();

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