package com.calidadagua.locations.features.waterbodies.list;

import com.calidadagua.locations.shared.dto.WaterBodyResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/water-bodies")
@RequiredArgsConstructor
public class ListWaterBodiesController {

    private final ListWaterBodiesHandler handler;

    @GetMapping
    public List<WaterBodyResponse> list() {

        return handler.execute();
    }
}