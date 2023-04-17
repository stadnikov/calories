DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO public.meals (id, user_id, date_time, description, calories)
VALUES (DEFAULT, 100000, '2023-04-17 09:51:20.000000', 'Завтрак', 750),
       (DEFAULT, 100000, '2023-04-17 11:51:20.000000', 'Завтрак второй', 250),
       (DEFAULT, 100000, '2023-04-17 15:51:20.000000', 'Обед', 800),
       (DEFAULT, 100000, '2023-04-17 19:51:20.000000', 'Ужин', 500),
       (DEFAULT, 100001, '2023-04-17 09:51:20.000000', 'Завтрак админа', 1500),
       (DEFAULT, 100001, '2023-04-17 13:51:20.000000', 'Обед админа', 2500),
       (DEFAULT, 100001, '2023-04-17 20:51:20.000000', 'Ужин админа', 500),
       (DEFAULT, 100001, '2023-04-18 21:51:20.000000', 'Ужин админа 2', 1500);
