package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ADMIN_ID, UserTestData.ADMIN_ID);
        assertMatch(meal, adminBreakfast);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() {
        Meal meal = service.get(MEAL_ADMIN_ID, UserTestData.USER_ID);
        assertMatch(meal, adminBreakfast);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL, UserTestData.USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ADMIN_ID, UserTestData.ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ADMIN_ID, UserTestData.ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() {
        service.delete(MEAL_ADMIN_ID, UserTestData.USER_ID);
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL, UserTestData.USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2023, 4, 18),
                LocalDate.of(2023, 4, 18), UserTestData.ADMIN_ID);
        assertMatch(meals, adminDinner2);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(UserTestData.ADMIN_ID);
        assertMatch(meals, adminDinner2, adminDinner, adminLunch, adminBreakfast);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, UserTestData.ADMIN_ID);
        assertMatch(service.get(updated.getId(), UserTestData.ADMIN_ID), getUpdated());
    }

    @Test
    public void updateAnotherUserMeal() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, UserTestData.USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), UserTestData.USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(
                new Meal(LocalDateTime.of(2023, 4, 17, 9, 51, 20), "New", 1),
                UserTestData.USER_ID));
    }
}