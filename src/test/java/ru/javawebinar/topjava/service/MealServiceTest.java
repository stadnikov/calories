package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.DataJpaMealServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaMealServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                DataJpaMealServiceTest.class,
                JdbcMealServiceTest.class,
                JpaMealServiceTest.class
        })
public class MealServiceTest {
}