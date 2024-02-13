package com.test.producertestapp.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pulsar")
@Component
@Data
public class PulsarProperties {
    private String url;
    private String topic;
    private String topic2;
}
