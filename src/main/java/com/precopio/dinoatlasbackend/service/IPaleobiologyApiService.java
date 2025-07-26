package com.precopio.dinoatlasbackend.service;

import reactor.core.publisher.Mono;

public interface IPaleobiologyApiService {
    Mono<Void> syncAllDinosaurs();
}
