package com.calidadagua.locations.features.waterbodies.getbyid;

import com.calidadagua.locations.shared.dto.WaterBodyResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/water-bodies")
@RequiredArgsConstructor
public class GetWaterBodyController {

    private final GetWaterBodyHandler handler;

    @GetMapping("/{id}")
    public WaterBodyResponse getById(@PathVariable UUID id) {

        return handler.execute(id);
    }
}