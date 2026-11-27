package com.calidadagua.locations.features.municipalities.list;

import com.calidadagua.locations.shared.dto.MunicipalityResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class ListMunicipalitiesController {

    private final ListMunicipalitiesHandler handler;

    @GetMapping
    public List<MunicipalityResponse> list() {

        return handler.execute();
    }
}