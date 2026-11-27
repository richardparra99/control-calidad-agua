package com.calidadagua.locations.features.communities.getbyid;

import com.calidadagua.locations.shared.dto.CommunityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class GetCommunityController {

    private final GetCommunityHandler handler;

    @GetMapping("/{id}")
    public CommunityResponse getById(@PathVariable UUID id) {

        return handler.execute(id);
    }
}