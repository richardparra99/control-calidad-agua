package com.calidadagua.locations.features.communities.listbymunicipality;

import com.calidadagua.locations.infrastructure.persistence.*;
import com.calidadagua.locations.shared.dto.CommunityResponse;
import com.calidadagua.locations.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListCommunitiesByMunicipalityHandler {

    private final CommunityRepository repository;
    private final MunicipalityRepository parentRepository;

    @Transactional(readOnly = true)
    public List<CommunityResponse> execute(UUID municipalityId) {

        if (!parentRepository.existsById(municipalityId)) {

            throw new ResourceNotFoundException(
                    "Municipality not found"
            );
        }

        return repository.findByMunicipality_Id(municipalityId)
                .stream()
                .map(CommunityResponse::from)
                .toList();
    }
}