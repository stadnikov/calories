package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>(repository.values());
        users.sort(Comparator.comparing(AbstractNamedEntity::getName));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user = repository.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
        if (!user.isPresent()) {
            throw new NotFoundException("Not found user with email="+email);
        } else {
            return user.get();
        }
    }
}
