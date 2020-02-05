package com.jgy.micro.config;

import com.jgy.micro.service.InfoService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@EnableConfigurationProperties(MicroProperties.class)
@Configuration
public class AutoConfig {

    private MicroProperties properties;
    private WebClient.Builder webClientBuilder;

    AutoConfig(MicroProperties properties, WebClient.Builder webClientBuilder) {
        this.properties = properties;
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    InfoService getInfoService() {
        return new InfoService(this.properties, this.webClientBuilder);
    }
}
