package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_USER_ID = START_SEQ + 3;
    public static final int MEAL_ADMIN_ID = START_SEQ + 7;
    public static final int NOT_FOUND_MEAL = 10;

    public static final Meal userBreakfast = new Meal(MEAL_USER_ID,
            LocalDateTime.of(2023, 4, 17, 9, 51, 20), "Завтрак", 750);
    public static final Meal userSecondBreakfast = new Meal(MEAL_USER_ID + 1,
            LocalDateTime.of(2023, 4, 17, 11, 51, 20), "Завтрак второй", 250);
    public static final Meal userLunch = new Meal(MEAL_USER_ID + 2,
            LocalDateTime.of(2023, 4, 17, 15, 51, 20), "Обед", 800);
    public static final Meal userDinner = new Meal(MEAL_USER_ID + 3,
            LocalDateTime.of(2023, 4, 17, 19, 51, 20), "Ужин", 500);

    public static final Meal adminBreakfast = new Meal(MEAL_ADMIN_ID,
            LocalDateTime.of(2023, 4, 17, 9, 51, 20), "Завтрак админа", 1500);
    public static final Meal adminLunch = new Meal(MEAL_ADMIN_ID + 1,
            LocalDateTime.of(2023, 4, 17, 13, 51, 20), "Обед админа", 2500);
    public static final Meal adminDinner = new Meal(MEAL_ADMIN_ID + 2,
            LocalDateTime.of(2023, 4, 17, 20, 51, 20), "Ужин админа", 500);
    public static final Meal adminDinner2 = new Meal(MEAL_ADMIN_ID + 3,
            LocalDateTime.of(2023, 4, 18, 21, 51, 20), "Ужин админа 2", 1500);


    public static Meal getNew() {
        return new Meal(
                LocalDateTime.of(2020, 12, 31, 23, 59, 59), "New", 1200);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminDinner2);
        updated.setDateTime(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        updated.setDescription("New Description");
        updated.setCalories(1500);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
