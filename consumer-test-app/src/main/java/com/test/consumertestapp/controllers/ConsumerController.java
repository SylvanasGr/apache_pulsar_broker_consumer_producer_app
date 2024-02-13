package com.test.consumertestapp.controllers;

import com.test.consumertestapp.model.ExampleObj;
import com.test.consumertestapp.service.impl.CustomWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConsumerController {

    private final CustomWebClientService customWebClientService;

    @GetMapping("/single/{value}")
    public Mono<ExampleObj> getExampleObject(@PathVariable String value) {
        return customWebClientService.getExampleObject(value);
    }

    @GetMapping("/list/{value}")
    public Flux<ExampleObj> getExampleObjects(@PathVariable String value) {
        return customWebClientService.getExampleObjects(value);
    }

    @PostMapping("/obj")
    public Mono<Void> createDummyExampleObject(@RequestBody ExampleObj exampleObj) {
        return customWebClientService.createDummyExampleObject(exampleObj);
    }

    @GetMapping("/hello")
    public Flux<String> testList(@PathVariable String hello){
        return customWebClientService.dummyTestList(hello);
    }

}
