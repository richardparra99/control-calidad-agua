package com.calidadagua.locations.features.departments.getbyid;

import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.shared.dto.DepartmentResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetDepartmentHandler {

    private final DepartmentRepository repository;

    @Transactional(readOnly = true)
    public DepartmentResponse execute(UUID id) {

        return repository.findById(id)
                .map(DepartmentResponse::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"
                        )
                );
    }
}