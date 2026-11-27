package com.calidadagua.locations.features.municipalities.update;

import com.calidadagua.locations.shared.dto.MunicipalityResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class UpdateMunicipalityController {

    private final UpdateMunicipalityHandler handler;

    @PutMapping("/{id}")
    public MunicipalityResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMunicipalityRequest request) {

        return handler.execute(id, request);
    }
}