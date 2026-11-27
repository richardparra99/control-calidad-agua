package com.calidadagua.locations.features.municipalities.list;

import com.calidadagua.locations.infrastructure.persistence.MunicipalityRepository;
import com.calidadagua.locations.shared.dto.MunicipalityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListMunicipalitiesHandler {

    private final MunicipalityRepository repository;

    @Transactional(readOnly = true)
    public List<MunicipalityResponse> execute() {

        return repository.findAll()
                .stream()
                .map(MunicipalityResponse::from)
                .toList();
    }
}