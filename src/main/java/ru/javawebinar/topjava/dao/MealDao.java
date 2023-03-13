package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.InMemoryMapStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealDao implements MealDaoInterface {

    InMemoryMapStorage meals = new InMemoryMapStorage(MealsUtil.meals);

    @Override
    public void addOrUpdate(Meal meal) {
        meals.addOrUpdate(meal);
    }

    @Override
    public void delete(Integer id) {
        meals.delete(id);
    }

    @Override
    public List<MealTo> getAllTo() {
        return MealsUtil.filteredByStreams(meals.getMealList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDayLimit);
    }

    @Override
    public Meal getById(Integer id) {
        return meals.getMealById(id);
    }
}
