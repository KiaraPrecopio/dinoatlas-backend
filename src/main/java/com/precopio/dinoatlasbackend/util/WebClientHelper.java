package com.precopio.dinoatlasbackend.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientHelper {

    private final WebClient webClient;

    public WebClientHelper(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T getObject(String uri, Class<T> clazz) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(clazz)
                .block();
    }

    public <T> Mono<T> getObjectAsync(String uri, Class<T> clazz) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(clazz);
    }
}
