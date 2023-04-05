package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email1@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "userName", "email3@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "userName", "email2@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "userName", "email5@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "userName", "email4@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "fserName", "email6@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "bserName", "email7@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "zserName", "email8@mail.ru", "password", Role.ADMIN));
            System.out.println(adminUserController.getAll());
            System.out.println(adminUserController.getByMail("email1@mail.ru"));
            //System.out.println(adminUserController.getByMail("email@mail.ru"));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.getAllTos());
            System.out.println(DateTimeUtil.isBetweenHalfOpen(LocalDate.now(), LocalDate.now(), LocalDate.now()));

            //DateTimeUtil.isBetweenHalfOpen(11, LocalDate.now(), "abc");,
            //DateTimeUtil.isBetweenHalfOpen(LocalDateTime.now(), LocalDate.now(), LocalTime.now());

        }
    }
}
