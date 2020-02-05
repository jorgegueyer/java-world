package com.jgy.reactor.mono;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.jgy.reactor.util.TestValues.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Jorge Guerra Yerpes
 */
@Slf4j
public class ThenReturnTesting {

    @Test
    public void applyThenReturnAfterMonoError() {

        Mono<String> result = Mono.justOrEmpty(stringByDefault)
                .flatMap(value -> Mono.error(new Exception(value)))
                .doOnError(error -> log.error(error.getMessage()))
                .thenReturn(stringA);

        StepVerifier.create(result)
                    .consumeErrorWith(error ->
                            assertTrue(error.getMessage().contains(stringByDefault)))
                    .verify();
    }

    @Test
    public void applyThenReturnAfterMonoVoid() {

        Mono<String> result = Mono.justOrEmpty(stringByDefault)
                .flatMap(value -> Mono.just(stringB).then()) // Return a Mono Void
                .thenReturn(stringA);

        StepVerifier.create(result)
                .expectNext(stringA)
                .verifyComplete();
    }

    /**
     * The defaultIfEmpty operator is never reached after applying filter.
     */
    @Test
    public void applyThenReturnAfterFilter() {

        Mono<String> result = Mono.justOrEmpty(stringA)
                .filter(value -> value.contains(stringB))
                .doOnNext(log::info)
                .thenReturn(stringC)
                .defaultIfEmpty(stringByDefault);

        StepVerifier.create(result)
                .expectNext(stringC)
                .verifyComplete();
    }
}
