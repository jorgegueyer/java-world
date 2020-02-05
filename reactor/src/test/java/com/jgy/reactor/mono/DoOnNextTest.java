package com.jgy.reactor.mono;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.jgy.reactor.util.TestValues.stringA;
import static com.jgy.reactor.util.TestValues.stringB;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Jorge Guerra Yerpes
 */
public class DoOnNextTest {

    /**
     * Something to be executed on the side of the pipeline,
     * since it doesn’t change the sequence’s data.
     */
    @Test
    public void modifyValueIntoDoOnNextTesting(){
        Mono<String> result = Mono.justOrEmpty(stringA)
                .doOnNext(value -> value = value.concat(stringB));

        StepVerifier.create(result)
                .consumeNextWith(value -> assertTrue(value.contains(stringA)))
                .verifyComplete();

    }
}
