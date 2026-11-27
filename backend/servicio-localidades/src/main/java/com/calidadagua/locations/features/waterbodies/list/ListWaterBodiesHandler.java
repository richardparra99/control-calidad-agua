package com.calidadagua.locations.features.waterbodies.list;

import com.calidadagua.locations.infrastructure.persistence.WaterBodyRepository;
import com.calidadagua.locations.shared.dto.WaterBodyResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListWaterBodiesHandler {

    private final WaterBodyRepository repository;

    @Transactional(readOnly = true)
    public List<WaterBodyResponse> execute() {

        return repository.findAll()
                .stream()
                .map(WaterBodyResponse::from)
                .toList();
    }
}