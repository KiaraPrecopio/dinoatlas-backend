package com.precopio.dinoatlasbackend.controller;

import com.precopio.dinoatlasbackend.dto.paleobiologydb.TaxonResponseDTO;
import com.precopio.dinoatlasbackend.service.TaxonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dinosaurs")
@RequiredArgsConstructor
@Tag(name = "Dinosaurs", description = "Dinosaur data management")
public class TaxonController {

    private final TaxonService taxonService;

    @GetMapping("/search")
    @Operation(summary = "Search dinosaurs", description = "Search dinosaurs by name")
    public ResponseEntity<Page<TaxonResponseDTO>> searchDinosaurs(@RequestParam String name,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taxonService.searchDinosaurs(name, page, size));
    }

    @GetMapping("/count")
    @Operation(summary = "Get total species count", description = "Get total number of dinosaur species")
    public ResponseEntity<Long> getTotalSpeciesCount() {
        Long count = taxonService.getTotalSpeciesCount();
        return ResponseEntity.ok(count);
    }
}