package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                DataJpaUserServiceTest.class,
                JdbcUserServiceTest.class,
                JpaUserServiceTest.class
        })
public class UserServiceTest {
}