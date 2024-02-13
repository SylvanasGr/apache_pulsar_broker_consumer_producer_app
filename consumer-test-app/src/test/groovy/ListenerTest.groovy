import com.test.consumertestapp.model.ExampleObj
import org.apache.pulsar.client.api.*
import spock.lang.Specification

class ListenerTest extends Specification {

    def "should consume a message sent to Pulsar"() {
        given:
        PulsarClient pulsarClient = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build()
        Producer<ExampleObj> producer = pulsarClient.newProducer(Schema.JSON(ExampleObj.class)).topic("example-topic").create()

        ExampleObj exampleObj = new ExampleObj()
        exampleObj.setName("John Doe")
        exampleObj.setAge(30)

        when:
        producer.send(exampleObj)

        then:
        def messagesReceived = []
        Thread consumerThread = new Thread({
            Consumer<ExampleObj> exampleObjConsumer = pulsarClient.newConsumer(Schema.JSON(ExampleObj.class)).topic("example-topic").subscriptionName("test-subscription").subscribe()
            while (messagesReceived.size() < 1) {
                Message<ExampleObj> msg = exampleObjConsumer.receive()
                messagesReceived.add(msg)
                exampleObjConsumer.acknowledge(msg)
            }
            exampleObjConsumer.close()
        })
        consumerThread.start()

        // wait for the consumer thread to finish
        consumerThread.join()

        // assert that the message was received
        assert messagesReceived.size() == 1
        assert messagesReceived[0].getValue().getName() == "John Doe"
        assert messagesReceived[0].getValue().getAge() == 30
    }
}
