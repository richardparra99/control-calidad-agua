package com.calidadagua.locations.features.municipalities.getbyid;

import com.calidadagua.locations.infrastructure.persistence.MunicipalityRepository;
import com.calidadagua.locations.shared.dto.MunicipalityResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetMunicipalityHandler {

    private final MunicipalityRepository repository;

    @Transactional(readOnly = true)
    public MunicipalityResponse execute(UUID id) {

        return repository.findById(id)
                .map(MunicipalityResponse::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Municipality not found"
                        )
                );
    }
}