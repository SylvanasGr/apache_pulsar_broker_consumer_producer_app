package com.test.consumertestapp.service;

import com.test.consumertestapp.configs.EndpointsConfig;
import com.test.consumertestapp.model.ExampleObj;
import com.test.consumertestapp.service.impl.CustomWebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomWebClientServiceImpl implements CustomWebClientService {

    private final WebClient webClient;
    private final EndpointsConfig endpointsConfig;

    @Override
    public Mono<ExampleObj> getExampleObject(String value) {
        log.info("Calling getExampleObject with value {}", value);
        return webClient
                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .path(endpointsConfig.getSingle())
//                        .queryParam("hello", value)
//                        .build())
                .uri(endpointsConfig.getSingle() + value)
                .retrieve()
                .bodyToMono(ExampleObj.class)
                .doOnSuccess(exampleObj -> log.info("Received example object: {}", exampleObj))
                .doOnError(throwable -> log.error("Error getting example object: {}", throwable.getMessage()));
    }

    @Override
    public Flux<ExampleObj> getExampleObjects(String value) {
        log.info("Calling getExampleObjects with value {}", value);
        return webClient
                .get()
                .uri(endpointsConfig.getList() + value)
                .retrieve()
                .bodyToFlux(ExampleObj.class)
                .doOnNext(exampleObj -> log.info("Received example object: {}", exampleObj))
                .doOnError(throwable -> log.error("Error getting example objects: {}", throwable.getMessage()));
    }


    @Override
    public Mono<Void> createDummyExampleObject(ExampleObj exampleObj) {
        log.info("Calling createDummyExampleObject with exampleObj {}", exampleObj);
        return webClient.post()
                .uri(endpointsConfig.getObj())
                .body(Mono.just(exampleObj), ExampleObj.class)
                .retrieve()
                .onStatus(HttpStatus::isError, res -> Mono.error(new RuntimeException("Error creating dummy example object: " + res.statusCode())))
                .bodyToMono(Void.class)
                .doOnSuccess(dummy -> log.info("Successfully created dummy example object"))
                .doOnError(throwable -> log.error("Error creating dummy example object: {}", throwable.getMessage()));
    }

    @Override
    public Flux<String> dummyTestList(String hello) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(endpointsConfig.getDummy())
                        .queryParam("hello", hello)
                        .build())
                .retrieve()
                .bodyToFlux(String.class);
    }

}
