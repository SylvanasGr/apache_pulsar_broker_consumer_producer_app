package controller

import com.test.producertestapp.Controller.ProducerController
import com.test.producertestapp.model.ExampleObj
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
class ProducerControllerTests extends Specification {
    private String endpoint
    private def controller
    private MockMvc mockMvc
    private String dummyValue = "hello_world"
    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        (endpoint, controller) = createSut()
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def createSut(){
        def endpoint = "/api"
        def controller = new ProducerController()
        return [endpoint, controller]
    }

    def "Should return one single exampleObj"(){
        given:
        endpoint = endpoint.concat("/single/")
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(endpoint + dummyValue )).andReturn().response
        def responseBody = response.getContentAsString()
        def exampleObj = objectMapper.readValue(responseBody, ExampleObj.class)
        then:
        response.status == HttpStatus.OK.value()
        exampleObj.name == dummyValue
    }

    def "Should return a list of exampleObjs"(){
        given:
        endpoint = endpoint.concat("/list/")
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(endpoint + dummyValue )).andReturn().response
        def exampleObj = objectMapper.readValue(response.getContentAsString(), List<ExampleObj>.class)
        then:
        response.status == HttpStatus.OK.value()
        exampleObj.size() == 3
    }


    def "Should return nothing in case of Success"(){
        given:
        endpoint = endpoint.concat("/obj")
        ExampleObj exampleObj = ExampleObj.builder().name("test").age(30).build();
        when:

        def response = mockMvc.
                perform(MockMvcRequestBuilders.post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exampleObj))).andReturn().response
        then:
        response.status == HttpStatus.OK.value()
    }

    def "Should return 404 in case of dummy 'error' value"(){
        given:
        endpoint = endpoint.concat("/obj")
        ExampleObj exampleObj = ExampleObj.builder().name("error").age(30).build();
        when:

        def response = mockMvc.
                perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exampleObj))).andReturn().response
        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

}
