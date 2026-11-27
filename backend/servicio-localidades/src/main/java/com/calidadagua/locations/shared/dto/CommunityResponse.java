package com.calidadagua.locations.shared.dto;

import com.calidadagua.locations.domain.model.Community;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommunityResponse(

        UUID id,
        String name,
        UUID municipalityId,
        String municipalityName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static CommunityResponse from(Community entity) {

        return new CommunityResponse(
                entity.getId(),
                entity.getName(),
                entity.getMunicipality().getId(),
                entity.getMunicipality().getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}