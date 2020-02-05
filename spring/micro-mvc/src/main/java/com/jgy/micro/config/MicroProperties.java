package com.jgy.micro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("micro")
@Data
@Validated
public class MicroProperties {

    private Service service = new Service();
    private Validation validation = new Validation();

    @Data
    public static class Service {
        private String endpoint;
    }

    @Data
    public static class Validation {
        private String endpoint;
    }
}
