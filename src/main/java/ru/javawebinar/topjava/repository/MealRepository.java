package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Integer userId, Meal meal);

    // false if meal does not belong to userId
    boolean delete(Integer userId, int id);

    // null if meal does not belong to userId
    Meal get(Integer userId, int id);

    // ORDERED dateTime desc
    Collection<Meal> getAll(Integer userId);

    Collection<Meal> getAll(Integer userId, Predicate<Meal> filterBy);

    Collection<Meal> getAll(Integer userId, Predicate<Meal> filterBy, Comparator<Meal> sortedBy);

    Collection<Meal> getAllFiltered(Integer userId, LocalDate startDate, LocalDate endDate);
}
