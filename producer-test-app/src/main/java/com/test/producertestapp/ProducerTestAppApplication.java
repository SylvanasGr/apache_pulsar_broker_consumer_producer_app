package com.test.producertestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ProducerTestAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerTestAppApplication.class, args);
    }
}
