package com.precopio.dinoatlasbackend.controller;

import com.precopio.dinoatlasbackend.model.entity.Taxon;
import com.precopio.dinoatlasbackend.service.ITaxonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dinosaurs")
@RequiredArgsConstructor
@Tag(name = "Dinosaurs", description = "Dinosaur data management")
public class TaxonController {

    private final ITaxonService taxonService;

    @GetMapping
    @Operation(summary = "Get all dinosaurs", description = "Retrieve all dinosaur species from database")
    public ResponseEntity<List<Taxon>> getAllDinosaurs() {
        List<Taxon> dinosaurs = taxonService.getAllDinosaurs();
        return ResponseEntity.ok(dinosaurs);
    }

    @GetMapping("/search")
    @Operation(summary = "Search dinosaurs", description = "Search dinosaurs by name")
    public ResponseEntity<List<Taxon>> searchDinosaurs(@RequestParam String name) {
        List<Taxon> dinosaurs = taxonService.searchDinosaurs(name);
        return ResponseEntity.ok(dinosaurs);
    }

    @GetMapping("/count")
    @Operation(summary = "Get total species count", description = "Get total number of dinosaur species")
    public ResponseEntity<Long> getTotalSpeciesCount() {
        Long count = taxonService.getTotalSpeciesCount();
        return ResponseEntity.ok(count);
    }
}