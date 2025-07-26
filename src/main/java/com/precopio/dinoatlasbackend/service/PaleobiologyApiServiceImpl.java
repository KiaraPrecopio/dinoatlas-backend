package com.precopio.dinoatlasbackend.service;

import com.precopio.dinoatlasbackend.dto.TaxonListResponseDTO;
import com.precopio.dinoatlasbackend.dto.TaxonResponseDTO;
import com.precopio.dinoatlasbackend.util.WebClientHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaleobiologyApiServiceImpl implements IPaleobiologyApiService {

    private final WebClientHelper webClientHelper;
    private final ITaxonService taxonService;

    public Mono<Void> syncAllDinosaurs() {
        log.info("Starting dinosaur data synchronization...");

        String url = "/taxa/list.json?name=Dinosauria&rel=all_children&rank=species&show=full";

        return webClientHelper.getObjectAsync(url, TaxonListResponseDTO.class)
                .doOnNext(response -> log.info("Received {} dinosaur records",
                        response.getRecords() != null ? response.getRecords().size() : 0))
                .flatMap(this::processTaxonList)
                .doOnSuccess(v -> log.info("Dinosaur data synchronization completed successfully"))
                .doOnError(error -> log.error("Error during dinosaur data synchronization", error));
    }

    private Mono<Void> processTaxonList(TaxonListResponseDTO response) {
        if (response.getRecords() == null || response.getRecords().isEmpty()) {
            log.warn("No dinosaur records received from API");
            return Mono.empty();
        }

        List<TaxonResponseDTO> records = response.getRecords();
        log.info("Processing {} dinosaur records...", records.size());

        // Procesar todos los records de forma síncrona dentro del contexto reactivo
        return Mono.fromRunnable(() -> {
            int processed = 0;
            int errors = 0;

            for (TaxonResponseDTO record : records) {
                try {
                    taxonService.saveOrUpdateTaxon(record);
                    processed++;

                    if (processed % 100 == 0) {
                        log.info("Processed {} dinosaur records...", processed);
                    }
                } catch (Exception e) {
                    errors++;
                    log.error("Error processing taxon {}: {}", record.getTaxonId(), e.getMessage());
                }
            }

            log.info("Processing complete. Processed: {}, Errors: {}", processed, errors);
        });
    }

    public Mono<TaxonListResponseDTO> searchTaxonByName(String name) {
        String url = String.format("/taxa/list.json?name=%s&show=full", name);
        return webClientHelper.getObjectAsync(url, TaxonListResponseDTO.class);
    }
}
