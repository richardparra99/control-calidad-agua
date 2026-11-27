package com.calidadagua.locations.features.departments.create;

import jakarta.validation.constraints.*;

public record CreateDepartmentRequest(
        @NotBlank(message = "Department name is required")
        @Size(max = 100)
        String name
) {
}