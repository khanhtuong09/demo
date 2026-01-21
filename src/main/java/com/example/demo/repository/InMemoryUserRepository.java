package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository {

    private final ConcurrentMap<String, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null || user.getId().isEmpty()) {
            user.setId(String.valueOf(idGenerator.getAndIncrement()));
        }
        store.put(user.getId(), copy(user));
        return copy(store.get(user.getId()));
    }

    public Optional<User> findById(String id) {
        User u = store.get(id);
        return u == null ? Optional.empty() : Optional.of(copy(u));
    }

    public Optional<User> findByEmail(String email) {
        for (User u : store.values()) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(copy(u));
            }
        }
        return Optional.empty();
    }

    public List<User> findAll() {
        List<User> out = new ArrayList<>();
        for (User u : store.values()) out.add(copy(u));
        return out;
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public void clear() {
        store.clear();
        idGenerator.set(1);
    }

    private User copy(User src) {
        if (src == null) return null;
        return new User(src.getId(), src.getName(), src.getEmail());
    }
}
