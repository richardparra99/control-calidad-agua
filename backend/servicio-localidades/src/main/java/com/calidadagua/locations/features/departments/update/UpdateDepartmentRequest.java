package com.calidadagua.locations.features.departments.update;

import jakarta.validation.constraints.*;

public record UpdateDepartmentRequest(

        @NotBlank(message = "Department name is required")
        @Size(max = 100)
        String name

) {
}