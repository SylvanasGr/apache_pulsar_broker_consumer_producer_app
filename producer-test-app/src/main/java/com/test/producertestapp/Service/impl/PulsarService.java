package com.test.producertestapp.Service.impl;


import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

public interface PulsarService {

    PulsarClient constructClient(String clientUrl);

    <T> Producer<T> addProducer(PulsarClient client,String clientTopic, Class<T> clazz);

    <T> void sendDummyMessage(Producer<T> producer, T obj);
}
