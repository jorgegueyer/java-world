package com.jgy.micro.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Slf4j
@Data
@Document(collection="info")
public class Info {

    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private String message;

    @NotBlank
    @Size(max = 200)
    private String description;
}
