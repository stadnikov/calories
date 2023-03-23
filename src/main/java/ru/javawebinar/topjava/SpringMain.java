package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email1@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "fserName", "email2@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "bserName", "email3@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "zserName", "email4@mail.ru", "password", Role.ADMIN));
            System.out.println(adminUserController.getAll());
            System.out.println(adminUserController.getByMail("email@mail.ru"));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.getAll());
        }
    }
}
