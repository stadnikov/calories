package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.DataJpaUserServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcUserServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaUserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                DataJpaUserServiceTest.class,
                JdbcUserServiceTest.class,
                JpaUserServiceTest.class
        })
public class UserServiceTest {
}