package com.calidadagua.locations.infrastructure.persistence;

import com.calidadagua.locations.domain.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommunityRepository
        extends JpaRepository<Community, UUID> {

    boolean existsByNameIgnoreCaseAndMunicipality_Id(
            String name, UUID municipalityId
    );

    boolean existsByNameIgnoreCaseAndMunicipality_IdAndIdNot(
            String name, UUID municipalityId, UUID id
    );

    List<Community> findByMunicipality_Id(UUID municipalityId);
}