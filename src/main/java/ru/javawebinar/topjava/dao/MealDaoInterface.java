package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDaoInterface {
    void addOrUpdate(Meal meal);
    void delete(Integer id);
    List<MealTo> getAllTo();
    Meal getById(Integer id);
}
