package com.calidadagua.locations.features.communities.create;

import com.calidadagua.locations.shared.dto.CommunityResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CreateCommunityController {

    private final CreateCommunityHandler handler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommunityResponse create(
            @Valid @RequestBody CreateCommunityRequest request) {

        return handler.execute(request);
    }
}