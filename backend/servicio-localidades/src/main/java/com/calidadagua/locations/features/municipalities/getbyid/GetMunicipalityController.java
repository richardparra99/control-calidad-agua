package com.calidadagua.locations.features.municipalities.getbyid;

import com.calidadagua.locations.shared.dto.MunicipalityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class GetMunicipalityController {

    private final GetMunicipalityHandler handler;

    @GetMapping("/{id}")
    public MunicipalityResponse getById(@PathVariable UUID id) {

        return handler.execute(id);
    }
}