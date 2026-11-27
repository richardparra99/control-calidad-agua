package com.calidadagua.locations.features.communities.create;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CreateCommunityRequest(

        @NotBlank(message = "Community name is required")
        @Size(max = 100)
        String name,

        @NotNull(message = "Municipality ID is required")
        UUID municipalityId

) {
}