package com.precopio.dinoatlasbackend.controller;


import com.precopio.dinoatlasbackend.scheduler.ApiScheduler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paleobiology")
@RequiredArgsConstructor
@Tag(name = "Paleobiology API", description = "Endpoints for accessing paleobiological data")
public class PaleobiologyApiController {

    private final ApiScheduler apiScheduler;

    @PostMapping("/sync/taxons")
    public void syncTaxons() {
        apiScheduler.syncTaxons();
    }

    @PostMapping("/sync/geological-intervals")
    public void syncGeologicalIntervals() {
        apiScheduler.syncGeologicalIntervals();
    }
}
