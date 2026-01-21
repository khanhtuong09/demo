package com.example.demo.repository;

import com.example.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryUserRepository();
        repo.clear();
    }

    @Test
    void saveAssignsIdAndFindByIdWorks() {
        User u = new User(null, "Alice", "alice@example.com");
        User saved = repo.save(u);
        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getName());

        var fetched = repo.findById(saved.getId());
        assertTrue(fetched.isPresent());
        assertEquals(saved, fetched.get());
    }

    @Test
    void findByEmailIsCaseInsensitive() {
        User u = new User(null, "Bob", "BOB@EXAMPLE.COM");
        repo.save(u);
        var fetched = repo.findByEmail("bob@example.com");
        assertTrue(fetched.isPresent());
        assertEquals("Bob", fetched.get().getName());
    }

    @Test
    void findAllAndDeleteWork() {
        repo.save(new User(null, "A", "a@x.com"));
        repo.save(new User(null, "B", "b@x.com"));
        List<User> all = repo.findAll();
        assertEquals(2, all.size());

        String idToDelete = all.get(0).getId();
        repo.deleteById(idToDelete);
        assertTrue(repo.findById(idToDelete).isEmpty());
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void clearResetsIdGenerator() {
        User u1 = repo.save(new User(null, "X", "x@x.com"));
        assertEquals("1", u1.getId());
        repo.clear();
        User u2 = repo.save(new User(null, "Y", "y@x.com"));
        assertEquals("1", u2.getId());
    }

    @Test
    void concurrentSavesProduceUniqueIdsAndNoLostWrites() throws InterruptedException, ExecutionException {
        int threads = 8;
        int perThread = 200;
        ExecutorService ex = Executors.newFixedThreadPool(threads);
        Set<String> ids = ConcurrentHashMap.newKeySet();

        Callable<Void> task = () -> {
            for (int i = 0; i < perThread; i++) {
                User u = new User(null, "T", Thread.currentThread().getName() + i + "@ex.com");
                User s = repo.save(u);
                assertNotNull(s.getId());
                ids.add(s.getId());
            }
            return null;
        };

        List<Future<Void>> futures = ex.invokeAll(java.util.Collections.nCopies(threads, task));
        for (Future<Void> f : futures) f.get();
        ex.shutdown();
        ex.awaitTermination(5, TimeUnit.SECONDS);

        int expected = threads * perThread;
        assertEquals(expected, ids.size());
        assertEquals(expected, repo.findAll().size());
    }
}

