package com.calidadagua.locations.features.municipalities.create;

import com.calidadagua.locations.shared.dto.MunicipalityResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class CreateMunicipalityController {

    private final CreateMunicipalityHandler handler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MunicipalityResponse execute(
            @Valid @RequestBody CreateMunicipalityRequest request) {

        return handler.execute(request);
    }
}