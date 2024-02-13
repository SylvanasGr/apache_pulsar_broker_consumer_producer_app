package com.test.consumertestapp;

import com.test.consumertestapp.configs.PulsarProperties;
import com.test.consumertestapp.model.ExampleObj;
import com.test.consumertestapp.model.ExampleObj2;
import com.test.consumertestapp.service.ConsumerPulsarServiceImpl;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private final PulsarProperties pulsarProperties;
    private final ConsumerPulsarServiceImpl consumerPulsarServiceImpl;

    public Listener(PulsarProperties pulsarProperties, ConsumerPulsarServiceImpl consumerPulsarServiceImpl) {
        this.pulsarProperties = pulsarProperties;
        this.consumerPulsarServiceImpl = consumerPulsarServiceImpl;
    }

    @Bean
    public void consumeMessages() {
        Consumer<ExampleObj> exampleObjConsumer = consumerPulsarServiceImpl.constructConsumer(ExampleObj.class, pulsarProperties.getUrl(), pulsarProperties.getTopic());
        Consumer<ExampleObj2> exampleObj2Consumer = consumerPulsarServiceImpl.constructConsumer(ExampleObj2.class, pulsarProperties.getUrl(), pulsarProperties.getTopic2());

        try {
            while (true) {
                Message<ExampleObj> msg = exampleObjConsumer.receive();
                System.out.printf("Topic: %s ,Received message: {Name: %s, Age: %s}\n", pulsarProperties.getTopic(), msg.getValue().getName(), msg.getValue().getAge());
                exampleObjConsumer.acknowledge(msg);

                Message<ExampleObj2> msg2 = exampleObj2Consumer.receive();
                System.out.printf("Topic: %s ,Received message: {Name: %s, Age: %s}\n", pulsarProperties.getTopic2(), msg2.getValue().getName(), msg2.getValue().getAge());
                exampleObj2Consumer.acknowledge(msg2);
            }
        } catch (PulsarClientException e) {
            throw new RuntimeException("Ops", e);
        }
    }
}
