package com.calidadagua.locations.features.departments.getbyid;

import com.calidadagua.locations.shared.dto.DepartmentResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class GetDepartmentController {

    private final GetDepartmentHandler handler;

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable UUID id) {

        return handler.execute(id);
    }
}