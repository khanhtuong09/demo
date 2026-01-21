package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String formatNowUtc() {
        return LocalDateTime.now(ZoneId.of("UTC")).format(formatter);
    }

    public LocalDateTime parse(String s) {
        if (s == null) throw new IllegalArgumentException("date string cannot be null");
        return LocalDateTime.parse(s, formatter);
    }
}

