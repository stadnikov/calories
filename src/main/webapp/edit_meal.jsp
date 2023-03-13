<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>

<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <table>
        <tr>
            <td>DateTime:</td>
            <td><input type="datetime-local" name="datetime" value="${meal.dateTime}"></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description" size="60" value="${meal.description}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" value="${meal.calories}"></td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Save">
                <button onclick="window.history.back()" type="button">Cancel</button>
            </td>
        </tr>
    </table>
</form>

</body>
</html>