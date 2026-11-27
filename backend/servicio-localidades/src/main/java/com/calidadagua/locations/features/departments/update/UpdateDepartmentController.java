package com.calidadagua.locations.features.departments.update;

import com.calidadagua.locations.shared.dto.DepartmentResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class UpdateDepartmentController {

    private final UpdateDepartmentHandler handler;

    @PutMapping("/{id}")
    public DepartmentResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDepartmentRequest request) {

        return handler.execute(id, request);
    }
}