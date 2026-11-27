package com.calidadagua.locations.features.waterbodies.listbycommunity;

import com.calidadagua.locations.shared.dto.WaterBodyResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class ListWaterBodiesByCommunityController {

    private final ListWaterBodiesByCommunityHandler handler;

    @GetMapping("/{communityId}/water-bodies")
    public List<WaterBodyResponse> listByCommunity(
            @PathVariable UUID communityId) {

        return handler.execute(communityId);
    }
}