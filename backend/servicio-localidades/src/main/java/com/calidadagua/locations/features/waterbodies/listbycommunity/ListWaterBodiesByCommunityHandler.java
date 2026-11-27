package com.calidadagua.locations.features.waterbodies.listbycommunity;

import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.WaterBodyResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListWaterBodiesByCommunityHandler {

    private final WaterBodyRepository repository;
    private final CommunityRepository parentRepository;

    @Transactional(readOnly = true)
    public List<WaterBodyResponse> execute(UUID communityId) {

        if (!parentRepository.existsById(communityId)) {

            throw new ResourceNotFoundException(
                    "Community not found"
            );
        }

        return repository.findByCommunity_Id(communityId)
                .stream()
                .map(WaterBodyResponse::from)
                .toList();
    }
}