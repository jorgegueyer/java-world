package com.jgy.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootTest
@EnableAutoConfiguration
@EnableWebFlux
public class EntryPointApplication {
	public static void main(String[] args) {
		SpringApplication.run(EntryPointApplication.class, args);
	}
}
