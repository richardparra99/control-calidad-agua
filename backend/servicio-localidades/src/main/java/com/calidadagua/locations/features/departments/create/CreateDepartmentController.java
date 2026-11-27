package com.calidadagua.locations.features.departments.create;

import com.calidadagua.locations.shared.dto.DepartmentResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class CreateDepartmentController {

    private final CreateDepartmentHandler handler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse create(
            @Valid @RequestBody CreateDepartmentRequest request) {

        return handler.execute(request);
    }
}