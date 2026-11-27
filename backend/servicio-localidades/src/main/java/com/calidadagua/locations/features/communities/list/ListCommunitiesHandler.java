package com.calidadagua.locations.features.communities.list;

import com.calidadagua.locations.infrastructure.persistence.CommunityRepository;
import com.calidadagua.locations.shared.dto.CommunityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCommunitiesHandler {

    private final CommunityRepository repository;

    @Transactional(readOnly = true)
    public List<CommunityResponse> execute() {

        return repository.findAll()
                .stream()
                .map(CommunityResponse::from)
                .toList();
    }
}