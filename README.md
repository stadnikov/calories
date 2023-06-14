[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0f03d9d7a81d4b1b949a2f6c53bb1aed)](https://app.codacy.com/gh/stadnikov/calories/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

Java Enterprise Online Project 
===============================
Запросы для idea rest api:

GET http://localhost:8080/topjava/rest/meals/
Accept: application/json

###

GET http://localhost:8080/topjava/rest/meals/100003

###

DELETE http://localhost:8080/topjava/rest/meals/1

###

DELETE http://localhost:8080/topjava/rest/meals/100003

###

GET http://localhost:8080/topjava/rest/meals/100003

###

POST http://localhost:8080/topjava/rest/meals
Content-Type: application/json

{
"dateTime": "2023-06-14T06:22",
"description": "тест",
"calories": 1000
}

###

PUT http://localhost:8080/topjava/rest/meals/100004
Content-Type: application/json
{
"id": 100004,
"dateTime": "2023-06-14T06:25",
"description": "тест",
"calories": 1000
}

###

GET http://localhost:8080/topjava/rest/meals/filter/?startDate=2010-01-01&startTime=00:00&endDate=2020-01-31&endTime=13:01
