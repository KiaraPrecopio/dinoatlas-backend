package com.precopio.dinoatlasbackend.scheduler;

import com.precopio.dinoatlasbackend.service.IPaleobiologyApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiScheduler {

    private final IPaleobiologyApiService paleobiologyApiService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateDatabase() {
        log.info("Starting scheduled dinosaur database update...");

        paleobiologyApiService.syncAllDinosaurs()
                .subscribe(
                        success -> log.info("Scheduled database update completed successfully"),
                        error -> log.error("Scheduled database update failed", error)
                );
    }
}