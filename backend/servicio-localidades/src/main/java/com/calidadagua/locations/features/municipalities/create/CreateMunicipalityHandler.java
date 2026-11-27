package com.calidadagua.locations.features.municipalities.create;

import com.calidadagua.locations.domain.model.*;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.*;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateMunicipalityHandler {

    private final MunicipalityRepository repository;
    private final DepartmentRepository parentRepository;

    @Transactional
    public MunicipalityResponse execute(
            CreateMunicipalityRequest request) {

        Municipality entity = new Municipality();

        Department parent = parentRepository
                .findById(request.departmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"
                        )
                );

        String name = request.name().trim();

        if (repository.existsByNameIgnoreCaseAndDepartment_Id(
                name, request.departmentId())) {

            throw new DuplicateResourceException(
                    "Municipality already exists"
            );
        }

        entity.setName(name);
        entity.setDepartment(parent);

        return MunicipalityResponse.from(
                repository.saveAndFlush(entity)
        );
    }
}