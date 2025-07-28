package com.precopio.dinoatlasbackend.service;

import reactor.core.publisher.Mono;

public interface PaleobiologyApiService {
    Mono<Void> syncAllTaxon(int limit, int offset);
    Mono<Void> syncAllIntervals();
}
