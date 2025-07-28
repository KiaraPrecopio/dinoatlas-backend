package com.precopio.dinoatlasbackend.scheduler;

import com.precopio.dinoatlasbackend.service.PaleobiologyApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiScheduler {

    private final PaleobiologyApiService paleobiologyApiService;

    public void syncGeologicalIntervals() {
        log.info("Starting geological intervals sync...");

        paleobiologyApiService.syncAllIntervals()
                .doOnSuccess(unused -> log.info("Geological intervals synchronization completed successfully"))
                .doOnError(error -> log.error("Error during geological intervals synchronization", error))
                .subscribe();

        log.info("Geological intervals sync completed.");
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void syncTaxons() {
        log.info("Starting taxons sync...");

        paleobiologyApiService.syncAllTaxon(1000, 0)
                .doOnSuccess(unused -> log.info("Taxons applied successfully"))
                .doOnError(error -> log.error("Error occurred during sync", error))
                .subscribe();

        log.info("Taxons sync completed.");
    }
}