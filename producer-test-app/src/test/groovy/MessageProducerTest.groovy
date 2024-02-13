import com.test.producertestapp.Config.PulsarProperties
import com.test.producertestapp.MessageProducer
import com.test.producertestapp.Service.PulsarServiceImpl
import com.test.producertestapp.model.ExampleObj
import org.apache.pulsar.client.api.Producer
import org.apache.pulsar.client.api.PulsarClient
import spock.lang.Specification

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class MessageProducerTest extends Specification {

    private PulsarServiceImpl pulsarService = mock(PulsarServiceImpl)
    private PulsarProperties pulsarProperties = mock(PulsarProperties)
    private PulsarClient pulsarClient = mock(PulsarClient)

    private MessageProducer messageProducer

    def setup() {
        when(pulsarProperties.getUrl()).thenReturn("pulsar://localhost:6650")
        when(pulsarProperties.getTopic()).thenReturn("potato-topic3")
        when(pulsarProperties.getTopic2()).thenReturn("potato-topic4")
        messageProducer = new MessageProducer(pulsarService, pulsarProperties)
    }

    def "Should send one dummy message"() {
        given:
        pulsarService.constructClient(pulsarProperties.getUrl()) >> pulsarClient
        Producer<ExampleObj> producerOne = pulsarService.addProducer(pulsarClient, pulsarProperties.getTopic(), ExampleObj.class)

        when:
        messageProducer.PulsarStartOne()

        then:
        pulsarService.sendDummyMessage(producerOne, new ExampleObj("Hello", 1000))

    }
}
