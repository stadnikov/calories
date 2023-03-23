package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> {
            if (oldMeal.getUserId() != userId) {
                return null;
            }
            return meal;
        });
    }

    @Override
    public boolean delete(Integer userId, int id) {
        // TODO atomic
        if (get(userId, id) == null) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(Integer userId, int id) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return getAll(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getAll(Integer userId, Predicate<Meal> filterBy) {
        return getAll(userId, filterBy, Comparator.comparing(Meal::getDateTime).reversed());
    }

    @Override
    public Collection<Meal> getAll(Integer userId, Predicate<Meal> filterBy, Comparator<Meal> sortedBy) {
        return repository.values().stream()
                .filter((meal) -> meal.getUserId().equals(userId))
                .filter(filterBy)
                .sorted(sortedBy)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<Meal> getAllFiltered(Integer userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId, (m) -> DateTimeUtil.isDateBetweenHalfOpen(LocalDateTime.of(m.getDate(), LocalTime.MIN)
                        , LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX)),
                Comparator.comparing(Meal::getDateTime).reversed());
    }
}

