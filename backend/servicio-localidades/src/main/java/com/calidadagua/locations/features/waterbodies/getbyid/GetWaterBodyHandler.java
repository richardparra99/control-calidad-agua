package com.calidadagua.locations.features.waterbodies.getbyid;

import com.calidadagua.locations.infrastructure.persistence.WaterBodyRepository;
import com.calidadagua.locations.shared.dto.WaterBodyResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetWaterBodyHandler {

    private final WaterBodyRepository repository;

    @Transactional(readOnly = true)
    public WaterBodyResponse execute(UUID id) {

        return repository.findById(id)
                .map(WaterBodyResponse::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Water body not found"
                        )
                );
    }
}