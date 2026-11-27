package com.calidadagua.locations.infrastructure.persistence;

import com.calidadagua.locations.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository
        extends JpaRepository<Department, UUID> {

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(
            String name, UUID id
    );
}