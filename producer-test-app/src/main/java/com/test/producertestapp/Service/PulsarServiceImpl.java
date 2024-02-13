package com.test.producertestapp.Service;

import com.test.producertestapp.Service.impl.PulsarService;
import lombok.AllArgsConstructor;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PulsarServiceImpl implements PulsarService {


    @Override
    public PulsarClient constructClient(String url) {
        try {
            return PulsarClient.builder()
                    .serviceUrl(url)
                    .build();
        } catch (PulsarClientException e) {
            throw new RuntimeException("An error occurred while creating the pulsar client", e);
        }
    }

    @Override
    public <T> Producer<T> addProducer(PulsarClient client,String topic, Class<T> clazz) {
        try {
            return client.newProducer(
                    Schema.JSON(clazz))
                    .topic(topic)
                    .compressionType(CompressionType.LZ4)
                    .create();
        } catch (PulsarClientException e) {
            throw new RuntimeException("An error occurred while adding a new producer", e);
        }
    }

    @Override
    public <T> void sendDummyMessage(Producer<T> producer, T obj) {
        try {
            TypedMessageBuilder<T> builder = (TypedMessageBuilder<T>) producer.newMessage(Schema.JSON(obj.getClass()));
            builder.value(obj).send();
            System.out.println("Sent message: " + obj);
        } catch (PulsarClientException e) {
            throw new RuntimeException("An error occurred while sending the msg to the consumer", e);
        }
    }
}
