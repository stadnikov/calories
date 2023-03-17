package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal addOrUpdate(Meal meal);

    void delete(int id);

    List<Meal> getList();

    Meal getById(int id);
}
