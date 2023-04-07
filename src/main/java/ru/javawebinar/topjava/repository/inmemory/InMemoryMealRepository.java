package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals1.forEach(meal -> save(1, meal));
        MealsUtil.meals2.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> innerMap = repository.computeIfAbsent(userId, v -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            Meal mealWithId = new Meal(counter.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
            innerMap.put(mealWithId.getId(), mealWithId);
            return mealWithId;
        } else {
            return innerMap.computeIfPresent(meal.getId(),
                    (id, oldMeal) -> new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        }
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> innerMap = repository.get(userId);
        if (innerMap == null) {
            return false;
        }
        return innerMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> innerMap = repository.get(userId);
        return innerMap != null ? innerMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAll(userId, meal -> true);
    }

    private List<Meal> getAll(int userId, Predicate<Meal> filterBy) {
        Map<Integer, Meal> innerMap = repository.get(userId);
        if (innerMap == null) {
            return Collections.emptyList();
        } else {
            return innerMap.values().stream()
                    .filter(filterBy)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId, m -> DateTimeUtil.isBetweenHalfOpen(LocalDateTime.of(m.getDate(), LocalTime.MIN),
                LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX)));
    }
}

