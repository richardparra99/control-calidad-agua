package com.calidadagua.locations.infrastructure.persistence;

import com.calidadagua.locations.domain.model.WaterBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WaterBodyRepository
        extends JpaRepository<WaterBody, UUID> {

    boolean existsByNameIgnoreCaseAndCommunity_Id(
            String name, UUID communityId
    );

    boolean existsByNameIgnoreCaseAndCommunity_IdAndIdNot(
            String name, UUID communityId, UUID id
    );

    List<WaterBody> findByCommunity_Id(UUID communityId);
}