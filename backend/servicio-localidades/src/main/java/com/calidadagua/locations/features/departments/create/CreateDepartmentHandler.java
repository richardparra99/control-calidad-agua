package com.calidadagua.locations.features.departments.create;

import com.calidadagua.locations.domain.model.Department;
import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.shared.dto.DepartmentResponse;
import com.calidadagua.locations.shared.exception.DuplicateResourceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CreateDepartmentHandler {

    private final DepartmentRepository repository;

    @Transactional
    public DepartmentResponse execute(
            CreateDepartmentRequest request) {

        String name = request.name().trim();

        if (repository.existsByNameIgnoreCase(name)) {

            throw new DuplicateResourceException(
                    "Department already exists"
            );
        }

        Department department = new Department();

        department.setName(name);

        return DepartmentResponse.from(
                repository.saveAndFlush(department)
        );
    }
}