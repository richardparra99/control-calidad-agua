package com.calidadagua.locations.shared.dto;

import com.calidadagua.locations.domain.model.Department;

import java.time.LocalDateTime;
import java.util.UUID;

public record DepartmentResponse(

        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static DepartmentResponse from(Department entity) {

        return new DepartmentResponse(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}