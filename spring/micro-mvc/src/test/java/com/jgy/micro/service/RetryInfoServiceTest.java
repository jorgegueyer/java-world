package com.jgy.micro.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock
public class RetryInfoServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void retryWhenValidationServiceFailsTest() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/validate")).willReturn(WireMock.badRequest().withBody("{\"error\":\"Server error\"}")));
        verify(exactly(0), getRequestedFor(urlEqualTo("/validate")));
        this.mockMvc.perform(get("/getValidateById").header("id", "jorge"));
        verify(exactly(2), getRequestedFor(urlEqualTo("/validate")));
    }
}
