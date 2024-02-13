package com.test.consumertestapp.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "endpoint")
@Data
public class EndpointsConfig {
    private String single;
    private String list;
    private String obj;
    private String dummy;
}
