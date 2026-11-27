package com.calidadagua.locations.features.municipalities.listbydepartment;

import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.MunicipalityResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListMunicipalitiesByDepartmentHandler {

    private final MunicipalityRepository repository;
    private final DepartmentRepository parentRepository;

    @Transactional(readOnly = true)
    public List<MunicipalityResponse> execute(UUID departmentId) {

        if (!parentRepository.existsById(departmentId)) {

            throw new ResourceNotFoundException(
                    "Department not found"
            );
        }

        return repository.findByDepartment_Id(departmentId)
                .stream()
                .map(MunicipalityResponse::from)
                .toList();
    }
}