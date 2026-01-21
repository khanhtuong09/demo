package com.example.demo.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest {

    private final DateService dateService = new DateService();

    @Test
    void parseAndFormatKnownString() {
        String s = "2020-01-02 03:04:05";
        LocalDateTime dt = dateService.parse(s);
        assertEquals(2020, dt.getYear());
        assertEquals(1, dt.getMonthValue());
        assertEquals(2, dt.getDayOfMonth());
        assertEquals(3, dt.getHour());
        assertEquals(4, dt.getMinute());
        assertEquals(5, dt.getSecond());
    }

    @Test
    void formatNowUtcReturnsNonNull() {
        String now = dateService.formatNowUtc();
        assertNotNull(now);
        assertTrue(now.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    void parseNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> dateService.parse(null));
    }
}

