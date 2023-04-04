package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        MealsUtil.meals.forEach(meal -> save(meal.getUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> innerMap =
                repository.get(userId) == null ? new ConcurrentHashMap<>() : new ConcurrentHashMap<>(repository.get(userId));
        Meal mealWithUserId;
        if (meal.isNew()) {
            mealWithUserId = new Meal(counter.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId);
        } else {
            Meal oldMeal = innerMap.get(meal.getId());
            if (oldMeal == null) {
                return null;
            }
            mealWithUserId = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId);
        }
        innerMap.put(mealWithUserId.getId(), mealWithUserId);
        repository.put(mealWithUserId.getUserId(), innerMap);
        return mealWithUserId;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> innerMap = new ConcurrentHashMap<>(repository.get(userId));
        if (get(userId, id) == null) {
            return false;
        }
        if (innerMap.remove(id) != null) {
            repository.put(userId, innerMap);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> innerMap = new ConcurrentHashMap<>(repository.get(userId));
        return innerMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAll(userId, meal -> true);
    }

    private List<Meal> getAll(int userId, Predicate<Meal> filterBy) {
        return getAll(userId, filterBy, Comparator.comparing(Meal::getDateTime).reversed());
    }

    private List<Meal> getAll(int userId, Predicate<Meal> filterBy, Comparator<Meal> sortedBy) {
        return repository.get(userId).values().stream()
                .filter(filterBy)
                .sorted(sortedBy)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId, m -> DateTimeUtil.isBetweenHalfOpen(LocalDateTime.of(m.getDate(), LocalTime.MIN),
                        LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX)),
                Comparator.comparing(Meal::getDateTime).reversed());
    }
}

