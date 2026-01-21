package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathServiceTest {

    private final MathService math = new MathService();

    @Test
    void addWorks() {
        assertEquals(5, math.add(2, 3));
    }

    @Test
    void subtractWorks() {
        assertEquals(1, math.subtract(3, 2));
    }

    @Test
    void multiplyWorks() {
        assertEquals(6, math.multiply(2, 3));
    }

    @Test
    void divideWorks() {
        assertEquals(2.5, math.divide(5, 2));
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(IllegalArgumentException.class, () -> math.divide(1, 0));
    }
}

