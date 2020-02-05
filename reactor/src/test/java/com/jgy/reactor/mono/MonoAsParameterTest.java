package com.jgy.reactor.mono;

import org.junit.Test;
import reactor.core.publisher.Mono;

import static com.jgy.reactor.util.TestValues.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonoAsParameterTest {

    @Test
    public void monoAsParameterMethodReturnABCTesting() {
        Object result = toBlock(Mono.justOrEmpty(stringA)
                                    .filter(value -> !value.isEmpty())
                                    .map(value -> value.concat(stringB))
                                    .defaultIfEmpty(stringA));

        assertTrue(result instanceof String);
        assertTrue(result.toString().contains(stringA+stringB+stringC));
    }

    @Test
    public void monoAsParameterMethodReturnACTesting() {
        Object result = toBlock(Mono.justOrEmpty(stringA)
                .filter(String::isEmpty)
                .map(value -> value.concat(stringB))
                .defaultIfEmpty(stringA));

        assertTrue(result instanceof String);
        assertTrue(result.toString().contains(stringA+stringC));
    }

    private Object toBlock(Mono<String> stream) {
        return stream.map(value -> value.concat(stringC))
                .block();
    }
}
