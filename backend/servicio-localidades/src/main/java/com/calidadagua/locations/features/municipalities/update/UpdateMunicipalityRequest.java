package com.calidadagua.locations.features.municipalities.update;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record UpdateMunicipalityRequest(

        @NotBlank(message = "Municipality name is required")
        @Size(max = 100)
        String name,

        @NotNull(message = "Department ID is required")
        UUID departmentId

) {
}