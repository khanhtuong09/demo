package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    private InMemoryUserRepository repo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        repo = new InMemoryUserRepository();
        repo.clear();
        userService = new UserService(repo);
    }

    @Test
    void createUserWorksAndNormalizesEmail() {
        User u = userService.createUser("Alice", "ALICE@EXAMPLE.COM");
        assertNull(u.getId());
        assertEquals("alice@example.com", u.getEmail());
    }

    @Test
    void createUserDuplicateEmailThrows() {
        userService.createUser("A", "a@x.com");
        assertThrows(IllegalArgumentException.class, () -> userService.createUser("B", "A@X.COM"));
    }

    @Test
    void updateUserChangesFields() {
        User u = userService.createUser("Tom", "t@x.com");
        User updated = userService.updateUser(u.getId(), "Tommy", "tommy@x.com");
        assertEquals("Tommy", updated.getName());
        assertEquals("tommy@x.com", updated.getEmail());
    }

    @Test
    void updateNonExistingThrows() {
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser("999", "X", "x@x.com"));
    }

    @Test
    void getAndListAndDeleteWork() {
        User a = userService.createUser("A", "a@x.com");
        User b = userService.createUser("B", "b@x.com");

        Optional<User> fetchA = userService.getById(a.getId());
        assertTrue(fetchA.isPresent());

        Optional<User> byEmail = userService.getByEmail("B@X.COM");
        assertTrue(byEmail.isPresent());

        List<User> all = userService.listAll();
        assertEquals(2, all.size());

        userService.delete(a.getId());
        assertTrue(userService.getById(a.getId()).isEmpty());
    }
}

