package com.test.consumertestapp.service;
import com.test.consumertestapp.service.impl.ConsumerPulsarService;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

@Service
public class ConsumerPulsarServiceImpl implements ConsumerPulsarService {

    @Override
    public <T> Consumer<T> constructConsumer(Class<T> clazz,String clientUrl, String topicName) {

        try {

            PulsarClient pulsarClient =
                    PulsarClient
                            .builder()
                            .serviceUrl(clientUrl)
                            .build();

            return pulsarClient.newConsumer(Schema.JSON(clazz))
                    .topic(topicName)
                    .subscriptionType(SubscriptionType.Shared)
                    .subscriptionName("Test-Sub")
                    .subscribe();

        } catch (PulsarClientException e) {
            throw new RuntimeException("An error occurred while initialized the client/consumer");
        }
    }

}
