package com.example.demo.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class CollectionServiceTest {

    private final CollectionService svc = new CollectionService();

    @Test
    void uniqueSortedRemovesDuplicatesAndSorts() {
        List<Integer> input = Arrays.asList(3, 1, 2, 3, 2, 4);
        List<Integer> result = svc.uniqueSorted(input);
        assertEquals(Arrays.asList(1,2,3,4), result);
    }

    @Test
    void uniqueSortedNullReturnsNull() {
        assertNull(svc.uniqueSorted(null));
    }

    @Test
    void sumReturnsCorrectSum() {
        assertEquals(10, svc.sum(Arrays.asList(1,2,3,4)));
    }

    @Test
    void sumHandlesNullsAndEmpty() {
        assertEquals(5, svc.sum(Arrays.asList(2, null, 3)));
        assertEquals(0, svc.sum(null));
    }
}

