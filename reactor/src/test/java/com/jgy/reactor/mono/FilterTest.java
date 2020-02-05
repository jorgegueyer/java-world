package com.jgy.reactor.mono;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.jgy.reactor.util.TestValues.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * How to work the filter operator
 * Discards all values that do not satisfy the condition
 * @author Jorge Guerra Yerpes
 */
public class FilterTest {

    @Test
    public void emptyStringIsEmptyTesting() {
        Mono<String> result = Mono.just(stringEmpty)
                .filter(String::isEmpty)
                .map(v -> stringA)
                .defaultIfEmpty(stringB);

        StepVerifier.create(result)
                .consumeNextWith(value ->
                        assertTrue(value.contains(stringA))
                )
                .verifyComplete();
    }

    @Test
    public void emptyStringIsNotEmptyTesting() {
        Mono<String> result = Mono.just(stringEmpty)
                .filter(value -> !value.isEmpty())
                .map(v -> stringA)
                .defaultIfEmpty(stringB);

        StepVerifier.create(result)
                .consumeNextWith(value ->
                        assertTrue(value.contains(stringB))
                )
                .verifyComplete();
    }
}
