package com.calidadagua.locations.features.waterbodies.create;

import com.calidadagua.locations.shared.dto.WaterBodyResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/water-bodies")
@RequiredArgsConstructor
public class CreateWaterBodyController {

    private final CreateWaterBodyHandler handler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WaterBodyResponse create(
            @Valid @RequestBody CreateWaterBodyRequest request) {

        return handler.execute(request);
    }
}