package com.calidadagua.locations.features.waterbodies.create;

import com.calidadagua.locations.domain.model.WaterBodyType;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CreateWaterBodyRequest(

        @NotBlank(message = "Water body name is required")
        @Size(max = 150)
        String name,

        @NotNull(message = "Water body type is required")
        WaterBodyType type,

        @Size(max = 2000)
        String description,

        @NotNull(message = "Latitude is required")
        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        Double latitude,

        @NotNull(message = "Longitude is required")
        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        Double longitude,

        @NotNull(message = "Community ID is required")
        UUID communityId

) {
}