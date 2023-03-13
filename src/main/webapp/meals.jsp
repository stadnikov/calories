<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="mealsToList" scope="request" type="java.util.List"/>
<jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .red_text {
            color: red;
        }
        .green_text {
            color: green;
        }
        table   {
            border-collapse: collapse;
            border: 1px solid black;
        }
        table  th {
            border: 1px solid black;
            padding: 5px;
        }
        table  td {
            border: 1px solid black;
            padding: 5px;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=insert">Add Meal</a>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>

    <c:forEach var="mealTo" items="${mealsToList}">
        <tr class="${mealTo.excess?'red_text':'green_text'}">
            <td>${mealTo.dateTime.format(dateTimeFormatter)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=edit&id=${mealTo.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${mealTo.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>