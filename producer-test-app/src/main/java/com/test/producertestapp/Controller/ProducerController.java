package com.test.producertestapp.Controller;

import com.test.producertestapp.model.ExampleObj;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProducerController {

    @GetMapping("/single/{value}")
    public ResponseEntity<ExampleObj> getSingleDummyObj(@PathVariable String value) {
        return new ResponseEntity<>(ExampleObj.builder().name(value).build(), HttpStatus.OK);
    }

    @GetMapping("/list/{value}")
    public ResponseEntity<List<ExampleObj>> getListDummyObj(@PathVariable String value) {
        return new ResponseEntity<>(List.of(
                ExampleObj.builder().name(value).age(100).build(),
                ExampleObj.builder().name(value).age(200).build(),
                ExampleObj.builder().name(value).age(300).build()
        ), HttpStatus.OK);
    }

    @PostMapping("/obj")
    public ResponseEntity<Void> createDummyObj(@RequestBody ExampleObj exampleObj) {

        if (exampleObj.getName().equals("error")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
