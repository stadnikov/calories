package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(profiles = {"jpa"}, resolver = ActiveDbProfileResolver.class)
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
