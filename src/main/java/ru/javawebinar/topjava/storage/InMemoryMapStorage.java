package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMapStorage {
    static AtomicInteger idCounter = new AtomicInteger(0);
    ConcurrentMap<Integer, Meal> mealMap;

    public InMemoryMapStorage(List<Meal> mealList) {
        this.mealMap = new ConcurrentHashMap<>();
        for (Meal meal : mealList) {
            addOrUpdate(meal);
        }
    }

    public void addOrUpdate(Meal meal) {
        if (meal.getId().intValue() == 0) {
            int id = idCounter.incrementAndGet();
            mealMap.put(id,
                    new Meal(new AtomicInteger(id), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        } else {
            mealMap.put(meal.getId().intValue(), meal);
        }

    }

    public void delete(Integer id) {
        mealMap.remove(id);
    }

    public List<Meal> getMealList() {
        return new ArrayList<>(mealMap.values());
    }

    public Meal getMealById(Integer id) {
        return mealMap.get(id);
    }
}
