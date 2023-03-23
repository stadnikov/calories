package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), SecurityUtil.authUserId(),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (meal.isNew()) {
            log.info("Create {}", meal);
            mealRestController.create(meal);
        } else {
            log.info("Update {}", meal);
            mealRestController.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");
        if ((userId != null) && (!userId.equals("null"))) {
            SecurityUtil.setAuthUserId(Integer.parseInt(userId));
        }
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(SecurityUtil.authUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String filterFromDate = request.getParameter("startdate");
                startDate = (filterFromDate == null) || (filterFromDate.equals("")) ? startDate : LocalDate.parse(filterFromDate);
                String filterToDate = request.getParameter("enddate");
                endDate = (filterToDate == null) || (filterToDate.equals("")) ? endDate : LocalDate.parse(filterToDate);
                String filterFromTime = request.getParameter("starttime");
                startTime = (filterFromTime == null) || (filterFromTime.equals("")) ? startTime : LocalTime.parse(filterFromTime);
                String filterToTime = request.getParameter("endtime");
                endTime = (filterToTime == null) || (filterToTime.equals("")) ? endTime : LocalTime.parse(filterToTime);
                log.info("Filter data: {}-{} {}-{}", startDate, endDate, startTime, endTime);

                request.setAttribute("startdate", startDate);
                request.setAttribute("enddate", endDate);
                request.setAttribute("starttime", startTime);
                request.setAttribute("endtime", endTime);
                request.setAttribute("meals", mealRestController.getAllTos());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
