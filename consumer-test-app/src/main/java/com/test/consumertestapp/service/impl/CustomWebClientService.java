package com.test.consumertestapp.service.impl;

import com.test.consumertestapp.model.ExampleObj;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomWebClientService {

    Mono<ExampleObj> getExampleObject(String value);

    Flux<ExampleObj> getExampleObjects(String value);

    Mono<Void> createDummyExampleObject(ExampleObj exampleObj);

    Flux<String> dummyTestList(String hello);

}
