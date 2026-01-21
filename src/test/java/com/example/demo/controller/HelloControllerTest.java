package com.example.demo.controller;

import com.example.demo.service.GreetingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloControllerTest {

    @Test
    void helloReturnsGreetingFromService() {
        GreetingService stub = new GreetingService() {
            @Override
            public String greet() {
                return "Hello World change";
            }
        };

        HelloController controller = new HelloController(stub);
        String result = controller.hello();
        assertEquals("Hello World change", result);
    }
}
