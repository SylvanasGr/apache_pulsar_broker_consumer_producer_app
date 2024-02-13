package com.test.consumertestapp.service.impl;

import org.apache.pulsar.client.api.Consumer;

public interface ConsumerPulsarService {
    <T> Consumer<T> constructConsumer(Class<T> clazz, String clientUrl, String topicName);
}
