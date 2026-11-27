package com.calidadagua.locations.features.waterbodies.update;

import com.calidadagua.locations.shared.dto.WaterBodyResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/water-bodies")
@RequiredArgsConstructor
public class UpdateWaterBodyController {

    private final UpdateWaterBodyHandler handler;

    @PutMapping("/{id}")
    public WaterBodyResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateWaterBodyRequest request) {

        return handler.execute(id, request);
    }
}