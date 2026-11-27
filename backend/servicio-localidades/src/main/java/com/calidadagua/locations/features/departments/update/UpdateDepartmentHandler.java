package com.calidadagua.locations.features.departments.update;

import com.calidadagua.locations.domain.model.Department;
import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.shared.dto.DepartmentResponse;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDepartmentHandler {

    private final DepartmentRepository repository;

    @Transactional
    public DepartmentResponse execute(
            UUID id, UpdateDepartmentRequest request) {

        Department department = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"
                        )
                );

        String name = request.name().trim();

        if (repository.existsByNameIgnoreCaseAndIdNot(name, id)) {

            throw new DuplicateResourceException(
                    "Department already exists"
            );
        }

        department.setName(name);

        return DepartmentResponse.from(
                repository.saveAndFlush(department)
        );
    }
}