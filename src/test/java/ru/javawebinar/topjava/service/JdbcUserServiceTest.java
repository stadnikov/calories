package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(profiles = {"jdbc"}, resolver = ActiveDbProfileResolver.class)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
