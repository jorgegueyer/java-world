package com.jgy.micro.controller;

import com.jgy.micro.config.MicroProperties;
import com.jgy.micro.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class InfoController {

    private WebClient webClient;

    @Autowired
    private InfoService infoService;

    public InfoController(WebClient.Builder webClientBuilder,
                          MicroProperties properties) {
        this.webClient = webClientBuilder.baseUrl(properties.getService().getEndpoint()).build();
    }

    @GetMapping("/getValidateById")
    public ResponseEntity<String> getValidateById(@RequestHeader("id") String headerParam) throws Throwable {
        return infoService.validate(headerParam) ? ResponseEntity.ok("Ok") : ResponseEntity.notFound().build();
    }

    @GetMapping("/getInfoById")
    public Mono<String> getInfoById(@RequestHeader("id") String headerParam) throws Throwable {
        return this.webClient.get()
                             .uri("/getInfo?id=" + headerParam)
                             .retrieve()
                             .bodyToMono(String.class);
    }

    @GetMapping("/getStringRemote")
    public Mono<String> getStringRemote() {
        return this.webClient.get()
                .uri("/getString")
                .retrieve()
                .bodyToMono(String.class)
                .map((String cadena) -> cadena.concat(" completa!"));
    }

    @GetMapping("/getString")
    public Mono<String> getString() {
        return Mono.just("Cadena");
    }
}
