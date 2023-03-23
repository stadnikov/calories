package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    public Collection<Meal> getAll(Predicate<Meal> filterBy) {
        return service.getAll(SecurityUtil.authUserId(), filterBy);
    }

    public List<MealTo> getAllTos() {
        return MealsUtil.getFilteredTos(service.getAllFiltered(SecurityUtil.authUserId(), startDate, endDate),
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id) {
        return service.get(SecurityUtil.authUserId(), id);
    }

    public Meal create(Meal meal) {
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }
}