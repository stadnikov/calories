[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0f03d9d7a81d4b1b949a2f6c53bb1aed)](https://app.codacy.com/gh/stadnikov/calories/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

Java Enterprise Online Project 
===============================
Get all meals for current user:
curl http://localhost:8080/topjava/rest/meals

Get specific (in this example meal = 100005) meal for current user:
curl http://localhost:8080/topjava/rest/meals/100005

Delete specific (in this example meal = 100005) meal for current user:
curl -X DELETE http://localhost:8080/topjava/rest/meals/100005

Create new meal with provided json object for current user:
curl -d "{\"dateTime\":\"2023-06-14T06:12\",\"description\":\"test\",\"calories\":1000}" -H "Content-Type:
application/json" http://localhost:8080/topjava/rest/meals

Update existing meal (in this example meal = 100012) with provided json object for current user:
curl -d "{\"dateTime\":\"2023-06-14T06:12\",\"description\":\"test\",\"calories\":1234}" -H "Content-Type:
application/json" -X PUT http://localhost:8080/topjava/rest/meals/100012

Filter current user meals with specific fields (startDate, endDate, startTime, endTime), if field is not exist rest
api will use default value:
curl "http://localhost:8080/topjava/rest/meals/filter/?startDate=2010-01-01&startTime=00:00&endDate=2020-01-
31&endTime=13:01"