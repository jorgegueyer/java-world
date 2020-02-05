package com.jgy.webflux.webtestclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jgy.webflux.EntryPointApplication;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(classes = EntryPointApplication.class,
        properties = "spring.main.web-application-type=SERVLET")
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServletWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @BeforeAll
    public void init() {
        stubFor(get("/test").willReturn(ok().withBody("Ok")));
    }

    @AfterAll
    public void destroy(){
        WireMock.removeAllMappings();
    }

    @Test
    public void webTestClientForServletApp() {
        this.webTestClient.get()
                            .uri("/test")
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(String.class)
                            .isEqualTo("Ok");
    }
}
