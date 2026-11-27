package com.calidadagua.locations.features.communities.update;

import com.calidadagua.locations.shared.dto.CommunityResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class UpdateCommunityController {

    private final UpdateCommunityHandler handler;

    @PutMapping("/{id}")
    public CommunityResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCommunityRequest request) {

        return handler.execute(id, request);
    }
}