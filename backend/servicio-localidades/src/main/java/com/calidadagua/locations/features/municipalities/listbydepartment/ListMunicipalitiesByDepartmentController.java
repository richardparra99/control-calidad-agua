package com.calidadagua.locations.features.municipalities.listbydepartment;

import com.calidadagua.locations.shared.dto.MunicipalityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class ListMunicipalitiesByDepartmentController {

    private final ListMunicipalitiesByDepartmentHandler handler;

    @GetMapping("/{departmentId}/municipalities")
    public List<MunicipalityResponse> listByDepartment(
            @PathVariable UUID departmentId) {

        return handler.execute(departmentId);
    }
}