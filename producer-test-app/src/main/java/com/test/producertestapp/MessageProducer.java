package com.test.producertestapp;

import com.test.producertestapp.Config.PulsarProperties;
import com.test.producertestapp.Service.PulsarServiceImpl;
import com.test.producertestapp.model.ExampleObj;
import com.test.producertestapp.model.ExampleObj2;
import lombok.AllArgsConstructor;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@EnableScheduling
public class MessageProducer {

    private final PulsarServiceImpl pulsarService;
    private final PulsarProperties pulsarProperties;

    @Scheduled(fixedRate = 5000)
    @Bean
    public void PulsarStartOne() {
        PulsarClient pulsarClient = pulsarService.constructClient(pulsarProperties.getUrl());
        Producer<ExampleObj> producer = pulsarService.addProducer(pulsarClient, pulsarProperties.getTopic(),ExampleObj.class);
        pulsarService.sendDummyMessage(producer, new ExampleObj("Hello",1000));
    }

    @Scheduled(fixedRate = 10000)
    @Bean
    public void PulsarStartTwo() {
        PulsarClient pulsarClient = pulsarService.constructClient(pulsarProperties.getUrl());
        Producer<ExampleObj2> producer = pulsarService.addProducer(pulsarClient, pulsarProperties.getTopic2(),ExampleObj2.class);
        pulsarService.sendDummyMessage(producer, new ExampleObj2("Hello2",2000,"nice"));
    }


}
