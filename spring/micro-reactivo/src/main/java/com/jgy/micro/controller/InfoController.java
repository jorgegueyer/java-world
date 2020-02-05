package com.jgy.micro.controller;

import com.jgy.micro.model.Info;
import com.jgy.micro.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class InfoController {

    private InfoRepository infoRepository;

    public InfoController(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    @GetMapping("/getInfo")
    public Mono<String> getDescriptionById(@RequestParam String id) {
        return this.infoRepository.findAll()
                                  .filter(reg -> !reg.getId().equals(id))
                                  .map((Info info) -> info.getDescription())
                                  .defaultIfEmpty("No values found!")
                                  .next();
    }

    @GetMapping("/getString")
    public Mono<String> getString() {
        return Mono.just("Cadena");
    }
}
