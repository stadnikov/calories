package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealStorage;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/editMeal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private MealDao dao;

    @Override
    public void init() {
        dao = new InMemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward;
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "insert":
                log.debug("action = insert");
                request.setAttribute("meal",
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 100));
                forward = INSERT_OR_EDIT;
                break;
            case "edit": {
                int mealId = Integer.parseInt(request.getParameter("id"));
                log.debug("action = edit, id = {}", mealId);
                Meal meal = dao.getById(mealId);
                request.setAttribute("meal", meal);
                forward = INSERT_OR_EDIT;
                break;
            }
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("id"));
                log.debug("action = delete, id = {}", mealId);
                dao.delete(mealId);
                response.sendRedirect("meals");
                return;
            default:
                forward = LIST_MEAL;
                request.setAttribute("mealsToList",
                        MealsUtil.filteredByStreams(dao.getList(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY_LIMIT));
                request.setAttribute("dateTimeFormatter", TimeUtil.DATE_TIME_FORMATTER);
                break;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("doing post");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Integer mealId;
        if (id == null || id.isEmpty()) {
            mealId = null;
        } else {
            mealId = Integer.parseInt(id);
        }
        dao.addOrUpdate(new Meal(mealId, dateTime, description, calories));

        response.sendRedirect("meals");
    }
}
