package com.precopio.dinoatlasbackend.service.impl;

import com.precopio.dinoatlasbackend.dto.paleobiologydb.GeologicalIntervalListResponseDTO;
import com.precopio.dinoatlasbackend.dto.paleobiologydb.GeologicalIntervalResponseDTO;
import com.precopio.dinoatlasbackend.dto.paleobiologydb.TaxonListResponseDTO;
import com.precopio.dinoatlasbackend.dto.paleobiologydb.TaxonResponseDTO;
import com.precopio.dinoatlasbackend.model.enums.GeologicalIntervalType;
import com.precopio.dinoatlasbackend.service.GeologicalIntervalService;
import com.precopio.dinoatlasbackend.service.PaleobiologyApiService;
import com.precopio.dinoatlasbackend.service.TaxonService;
import com.precopio.dinoatlasbackend.util.WebClientHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaleobiologyApiServiceImpl implements PaleobiologyApiService {

    private final WebClientHelper webClientHelper;
    private final TaxonService taxonService;
    private final GeologicalIntervalService intervalService;

    public Mono<Void> syncAllTaxon(int limit, int offset) {
        log.info("Starting taxon data synchronization...");

        String url = String.format(
                "/taxa/list.json?base_name=diapsida&taxon_status=accepted&rel=all_children&rank=species&show=full&limit=%d&offset=%d",
                limit, offset
        );

        return webClientHelper.getObjectAsync(url, TaxonListResponseDTO.class)
                .flatMap(response -> {
                    List<TaxonResponseDTO> records = response.getRecords();
                    if (records == null || records.isEmpty()) {
                        log.info("Finished syncing all taxons.");
                        return Mono.empty();
                    }

                    log.info("Fetched {} taxons at offset {}", records.size(), offset);

                    return Flux.fromIterable(records)
                            .buffer(100)
                            .flatMap(batch -> Mono.fromRunnable(() -> {
                                try {
                                    taxonService.saveOrUpdateTaxonBatch(batch);
                                } catch (Exception e) {
                                    log.error("Error saving batch of limit {} at offset {}: {}",
                                            limit, offset, e.getMessage(), e);
                                }
                            }).subscribeOn(Schedulers.boundedElastic()))
                            .then(syncAllTaxon(offset + limit, limit));
                });
    }


    public Mono<Void> syncAllIntervals() {
        log.info("Starting time interval synchronization...");

        String url = "/intervals/list.json?all_records&show=full";

        return webClientHelper.getObjectAsync(url, GeologicalIntervalListResponseDTO.class)
                .doOnNext(response -> log.info("Received {} time interval records",
                        response.getRecords() != null ? response.getRecords().size() : 0))
                .flatMap(this::processTimeIntervalList)
                .doOnSuccess(v -> log.info("Time interval synchronization completed successfully"))
                .doOnError(error -> log.error("Error during time interval synchronization", error));
    }

    private Mono<Void> processTimeIntervalList(GeologicalIntervalListResponseDTO response) {
        if (response.getRecords() == null || response.getRecords().isEmpty()) {
            log.warn("No time interval records received from API");
            return Mono.empty();
        }

        List<GeologicalIntervalResponseDTO> records = response.getRecords().stream()
                .filter(r -> r.getIntervalType() != null
                        && Arrays.stream(GeologicalIntervalType.values())
                                .anyMatch(e -> e.getValue().equalsIgnoreCase(r.getIntervalType())))
                .sorted(Comparator.comparingInt(r -> switch (r.getIntervalType().toLowerCase()) {
                    case "eon" -> 0;
                    case "era" -> 1;
                    case "period" -> 2;
                    case "epoch" -> 3;
                    case "age" -> 4;
                    default -> 5;
                }))
                .toList();
        log.info("Processing {} time interval records...", records.size());

        return Mono.fromRunnable(() -> {
            int processed = 0;
            int errors = 0;

            for (GeologicalIntervalResponseDTO record : records) {
                try {
                    intervalService.saveOrUpdateTimeInterval(record);
                    processed++;

                    if (processed % 100 == 0) {
                        log.info("Processed {} time interval records...", processed);
                    }
                } catch (Exception e) {
                    errors++;
                    log.error("Error processing time interval {}: {}", record.getIntervalId(), e.getMessage());
                }
            }

            log.info("Processing interval complete. Processed: {}, Errors: {}", processed, errors);
        });
    }
}
