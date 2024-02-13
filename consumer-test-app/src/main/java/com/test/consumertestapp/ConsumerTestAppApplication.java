package com.test.consumertestapp;

import com.test.consumertestapp.configs.EndpointsConfig;
import com.test.consumertestapp.configs.PulsarProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        EndpointsConfig.class,
        PulsarProperties.class
})
public class ConsumerTestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerTestAppApplication.class, args);
    }
}
