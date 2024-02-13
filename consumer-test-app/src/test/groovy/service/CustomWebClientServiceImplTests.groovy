package service

import com.test.consumertestapp.configs.EndpointsConfig
import com.test.consumertestapp.model.ExampleObj
import com.test.consumertestapp.service.CustomWebClientServiceImpl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.TimeUnit

@SpringBootTest
@AutoConfigureWebTestClient
class CustomWebClientServiceImplTests extends Specification {

    private EndpointsConfig endpointsConfig = Mock()

    @Subject
    private CustomWebClientServiceImpl customWebClientServiceImpl

    private MockWebServer mockWebServer

    void cleanup() {
        mockWebServer.shutdown()
    }

    @Before
    void setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        String rootUrl = "http://localhost:" + mockWebServer.getPort()
        customWebClientServiceImpl = new CustomWebClientServiceImpl(WebClient.create(rootUrl), endpointsConfig)
    }

    void shouldGetExampleObject() {
        given:
        def expectedExampleObj = new ExampleObj("test", 300)
        mockWebServer.enqueue(new MockResponse().setBody(expectedExampleObj))

        when:
        customWebClientServiceImpl.getExampleObject("test").block()

        then:
        RecordedRequest recordedRequest = mockWebServer.takeRequest(1, TimeUnit.SECONDS)
        recordedRequest.method == "GET"
    }
}
