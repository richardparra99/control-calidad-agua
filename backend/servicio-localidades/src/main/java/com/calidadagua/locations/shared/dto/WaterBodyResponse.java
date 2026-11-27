package com.calidadagua.locations.shared.dto;

import com.calidadagua.locations.domain.model.WaterBody;
import com.calidadagua.locations.domain.model.WaterBodyType;

import java.time.LocalDateTime;
import java.util.UUID;

public record WaterBodyResponse(

        UUID id,
        String name,
        WaterBodyType type,
        String description,
        Double latitude,
        Double longitude,
        UUID communityId,
        String communityName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static WaterBodyResponse from(WaterBody entity) {

        return new WaterBodyResponse(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getDescription(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCommunity().getId(),
                entity.getCommunity().getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
