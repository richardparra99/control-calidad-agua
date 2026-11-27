package com.calidadagua.locations.features.municipalities.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateMunicipalityRequest(
        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        UUID departmentId
) {
}
