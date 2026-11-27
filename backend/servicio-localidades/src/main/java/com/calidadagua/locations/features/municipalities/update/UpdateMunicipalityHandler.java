package com.calidadagua.locations.features.municipalities.update;

import com.calidadagua.locations.domain.model.*;
import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.MunicipalityResponse;
import com.calidadagua.locations.shared.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateMunicipalityHandler {

    private final MunicipalityRepository repository;
    private final DepartmentRepository parentRepository;

    @Transactional
    public MunicipalityResponse execute(
            UUID id, UpdateMunicipalityRequest request) {

        Municipality municipality = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Municipality not found"
                        )
                );

        Department department = parentRepository
                .findById(request.departmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"
                        )
                );

        String name = request.name().trim();

        if (repository
                .existsByNameIgnoreCaseAndDepartment_IdAndIdNot(
                        name, request.departmentId(), id)) {

            throw new DuplicateResourceException(
                    "Municipality already exists"
            );
        }

        municipality.setName(name);
        municipality.setDepartment(department);

        return MunicipalityResponse.from(
                repository.saveAndFlush(municipality)
        );
    }
}