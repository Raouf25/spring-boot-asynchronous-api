package com.mak.springbootasynchronousapi.service;

import com.mak.springbootasynchronousapi.exception.EntityMappingException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class Utils {

    private Utils() {
        // Private constructor to hide the implicit public one
    }

    static  <T> Mono<T> processWithErrorCheck(Mono<T> monoToCheck, String errorMessage) {
        return monoToCheck.switchIfEmpty(Mono.defer(() -> {
            log.error(errorMessage);
            return Mono.error(new EntityMappingException(errorMessage));
        }));
    }

    static  <T> Flux<T> processWithErrorCheck(Flux<T> fluxToCheck, String errorMessage) {
        return fluxToCheck.switchIfEmpty(Flux.defer(() -> {
            log.error(errorMessage);
            return Flux.error(new EntityMappingException(errorMessage));
        }));
    }

}
