package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.InMemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final InMemoryUserRepository repository;

    public UserService(InMemoryUserRepository repository) {
        this.repository = repository;
    }

    public User createUser(String name, String email) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email required");
        // ensure unique email
        Optional<User> existing = repository.findByEmail(email);
        if (existing.isPresent()) throw new IllegalArgumentException("email already exists");
        User u = new User(null, name, email.toLowerCase());
        return repository.save(u);
    }

    public User updateUser(String id, String name, String email) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        Optional<User> existing = repository.findById(id);
        if (existing.isEmpty()) throw new IllegalArgumentException("user not found");
        if (email != null) {
            Optional<User> byEmail = repository.findByEmail(email);
            if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
                throw new IllegalArgumentException("email already exists");
            }
        }
        User u = existing.get();
        if (name != null) u.setName(name);
        if (email != null) u.setEmail(email.toLowerCase());
        return repository.save(u);
    }

    public Optional<User> getById(String id) {
        return repository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> listAll() {
        return repository.findAll();
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
