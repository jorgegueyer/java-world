package com.jgy.aop.controller;

import com.jgy.aop.annotation.Annota;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class AnnotaController {

    @Annota
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public Mono<String> sayHello() {
        log.info("Method sayHello has been invoked");
        return Mono.just("Say Hello");
    }

    @Annota
    @RequestMapping(path = "/string/hello", method = RequestMethod.GET)
    public String sayHelloString() {
        log.info("Method sayHelloString has been invoked");
        return "Say Hello";
    }

    @RequestMapping(path="/service",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    method = RequestMethod.GET)
    public Mono<String> all() {
        return Mono.just("{'message':'I am a microservice'}");
    }

    @GetMapping("/sorry")
    public Mono<String> saySorry() {
        log.info("Method saySorry has been invoked");
        return Mono.just("Say Sorry");
    }
}
