package com.calidadagua.locations.features.departments.list;

import com.calidadagua.locations.shared.dto.DepartmentResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class ListDepartmentsController {

    private final ListDepartmentsHandler handler;

    @GetMapping
    public List<DepartmentResponse> list() {

        return handler.execute();
    }
}