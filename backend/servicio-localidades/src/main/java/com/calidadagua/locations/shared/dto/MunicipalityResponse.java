package com.calidadagua.locations.shared.dto;

import com.calidadagua.locations.domain.model.Municipality;

import java.time.LocalDateTime;
import java.util.UUID;

public record MunicipalityResponse(

        UUID id,
        String name,
        UUID departmentId,
        String departmentName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static MunicipalityResponse from(Municipality entity) {

        return new MunicipalityResponse(
                entity.getId(),
                entity.getName(),
                entity.getDepartment().getId(),
                entity.getDepartment().getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}