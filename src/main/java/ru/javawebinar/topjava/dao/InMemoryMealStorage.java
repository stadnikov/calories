package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealStorage implements MealDao {
    private static AtomicInteger idCounter = new AtomicInteger(0);
    private ConcurrentMap<Integer, Meal> mealMap;

    public InMemoryMealStorage() {
        this.mealMap = new ConcurrentHashMap<>();
        for (Meal meal : MealsUtil.meals) {
            addOrUpdate(meal);
        }
    }

    @Override
    public Meal addOrUpdate(Meal meal) {
        if (meal.getId() == null) {
            int id = idCounter.incrementAndGet();
            mealMap.put(id,
                    new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
            return mealMap.get(id);
        } else if (mealMap.get(meal.getId().intValue()) != null) {
            mealMap.put(meal.getId().intValue(), meal);
            return mealMap.get(meal.getId());
        }
        return null;
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> getMealList() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }
}
