package com.jgy.micro.service;

import com.jgy.micro.config.MicroProperties;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;

public class InfoService {

    private final WebClient webClient;

    public InfoService(MicroProperties properties, WebClient.Builder webClintBuilder) {
        this.webClient = webClintBuilder.baseUrl(properties.getValidation().getEndpoint()).build();
    }

    @Retry(name = "validate")
    public boolean validate(String id) {
        return this.validateId(id);
    }

    private boolean validateId(String id) {
        ValidateResponse validateResponse = this.webClient.get().retrieve().bodyToMono(ValidateResponse.class).block();
        return validateResponse != null && validateResponse.getState().equalsIgnoreCase("OK");
    }

    @Data
    private static class ValidateResponse {
        private String state;
    }
}
