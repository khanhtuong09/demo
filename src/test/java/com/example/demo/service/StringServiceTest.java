package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringServiceTest {

    private final StringService svc = new StringService();

    @Test
    void reverseNullReturnsNull() {
        assertNull(svc.reverse(null));
    }

    @Test
    void reverseWorks() {
        assertEquals("cba", svc.reverse("abc"));
    }

    @Test
    void isPalindromeTrue() {
        assertTrue(svc.isPalindrome("A man a plan a canal Panama"));
    }

    @Test
    void isPalindromeFalse() {
        assertFalse(svc.isPalindrome("hello"));
    }

    @Test
    void joinWorks() {
        assertEquals("a,b,c", svc.join(",", "a", "b", "c"));
    }
}

