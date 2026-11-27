package com.calidadagua.locations.features.communities.list;

import com.calidadagua.locations.shared.dto.CommunityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class ListCommunitiesController {

    private final ListCommunitiesHandler handler;

    @GetMapping
    public List<CommunityResponse> list() {

        return handler.execute();
    }
}