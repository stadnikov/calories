package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInterface;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String INSERT_OR_EDIT = "/edit_meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";
    private MealDaoInterface dao = new MealDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward = "";
        String action = request.getParameter("action");
        if (action == null) action = "";

        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("id"));
            log.debug("action = delete, id =" + mealId);
            dao.delete(mealId);
            forward = LIST_MEAL;
            request.setAttribute("mealsToList", dao.getAllTo());
        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("id"));
            log.debug("action = delete, id = " + mealId);
            Meal meal = dao.getById(mealId);
            request.setAttribute("meal", meal);
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("insert")) {
            log.debug("action = insert");
            request.setAttribute("meal", null);
            forward = INSERT_OR_EDIT;
        } else {
            forward = LIST_MEAL;
            request.setAttribute("mealsToList", dao.getAllTo());
        }

        request.setAttribute("dateTimeFormatter", TimeUtil.dateTimeFormatter);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doeing post");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        Integer calories = Integer.valueOf(request.getParameter("calories"));

        AtomicInteger atomicId;
        if (id == null || id.isEmpty()) {
            atomicId = new AtomicInteger(0);
        } else {
            atomicId = new AtomicInteger(Integer.parseInt(id));
        }
        dao.addOrUpdate(new Meal(atomicId, dateTime, description, calories));

        request.setAttribute("mealsToList", dao.getAllTo());
        request.setAttribute("dateTimeFormatter", TimeUtil.dateTimeFormatter);
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
