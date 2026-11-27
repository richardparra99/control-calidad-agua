package com.calidadagua.locations.infrastructure.persistence;

import com.calidadagua.locations.domain.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MunicipalityRepository
        extends JpaRepository<Municipality, UUID> {

    boolean existsByNameIgnoreCaseAndDepartment_Id(
            String name, UUID departmentId
    );

    boolean existsByNameIgnoreCaseAndDepartment_IdAndIdNot(
            String name, UUID departmentId, UUID id
    );

    List<Municipality> findByDepartment_Id(UUID departmentId);
}