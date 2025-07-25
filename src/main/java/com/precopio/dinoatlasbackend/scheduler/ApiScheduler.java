package com.precopio.dinoatlasbackend.scheduler;

import com.precopio.dinoatlasbackend.dto.TaxonListResponseDTO;
import com.precopio.dinoatlasbackend.util.WebClientHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor_=@Autowired)
public class ApiScheduler {
    private final WebClientHelper webClientHelper;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateDatabase() {
        String url = "/taxa/list.json?name=Dinosauria&rel=all_children&rank=species&show=full";

        Mono<TaxonListResponseDTO> response = webClientHelper.getObjectAsync(url, TaxonListResponseDTO.class);

        response.flatMap(taxonListResponse -> {
            // Process the response here, e.g., save to database
            // For now, just print the response
            System.out.println("Received taxon list: " + taxonListResponse);
            return Mono.empty(); // Return an empty Mono after processing
        }).subscribe();
        // Note: The subscribe() is necessary to trigger the asynchronous call.
    }
}
