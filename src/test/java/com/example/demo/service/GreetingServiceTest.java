package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceTest {

    @Test
    void greetReturnsExpectedString() {
        GreetingService service = new GreetingService();
        String result = service.greet();
        assertEquals("Hello World change", result);
    }
}

