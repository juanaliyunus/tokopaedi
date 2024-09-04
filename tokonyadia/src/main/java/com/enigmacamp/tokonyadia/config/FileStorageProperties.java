package com.enigmacamp.tokonyadia.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
}

