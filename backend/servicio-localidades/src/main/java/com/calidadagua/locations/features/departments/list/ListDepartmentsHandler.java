package com.calidadagua.locations.features.departments.list;

import com.calidadagua.locations.infrastructure.persistence.DepartmentRepository;
import com.calidadagua.locations.shared.dto.DepartmentResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListDepartmentsHandler {

    private final DepartmentRepository repository;

    @Transactional(readOnly = true)
    public List<DepartmentResponse> execute() {

        return repository.findAll()
                .stream()
                .map(DepartmentResponse::from)
                .toList();
    }
}