package com.calidadagua.locations.features.communities.listbymunicipality;

import com.calidadagua.locations.shared.dto.CommunityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class ListCommunitiesByMunicipalityController {

    private final ListCommunitiesByMunicipalityHandler handler;

    @GetMapping("/{municipalityId}/communities")
    public List<CommunityResponse> listByMunicipality(
            @PathVariable UUID municipalityId) {

        return handler.execute(municipalityId);
    }
}