package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(profiles = {"datajpa"}, resolver = ActiveDbProfileResolver.class)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
}
