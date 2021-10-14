## Time Data Security Ltd. [Documentation](https://tds-interview.herokuapp.com/swagger-ui.html) :thought_balloon:

![alt adidas](https://github.com/Nnadozieomeonu/images/blob/0d570b94ce1690df3174e14498b216adde548a01/Adam.gif?raw=true)

# Table of Contents :thought_balloon:
1. [Introduction and technology list](#Introduction-and-technology-list)
2. [Swagger API Documentation](https://tds-interview.herokuapp.com/swagger-ui.html).
3. [Problem Solving Question Solution](#Problem Solving Question Solution)


## Introduction and technology list :thought_balloon:

Coding Exercises
1.	You have the following API endpoint:
      https://postman-echo.com/get?foo1=bar1&foo2=bar2
      Please set up a quick page using any language. Call the API endpoint and display results

2.	Given the attached CSV data file - rest_open_hours.csv
      a.	Write a function that parses the data into a table
      b.	Write a function that receives a native to your language of choice and uses a sql query to return a list of restaurant names which are open on that date and time.


Assumptions:
* If a day of the week is not listed, the restaurant is closed on that day
* All times are local — don’t worry about timezone-awareness
* The CSV file will be well-formed

Optimized solutions are nice, but correct solutions are more important!

Note:  This is harder than it looks.


These are my notes on  how to set up the under listed service, technology for testing, my microservice architecture comprises the below microservice services.

1. [Spring-boot Framework](http://start.spring.io).
2. [H2 In-Memory Database](http://start.spring.io).
3. [Hibernate ORM](http://start.spring.io).
4. [GIT](http://git.com).
5. [InteliJ](http://jetbrains.com).

The Link to H2 Database is http://localhost:8080/h2-console
1. Database URL: ``https://tds-interview.herokuapp.com/h2-console/login.jsp``
2. Username: ``sa``
3. Password: ``password``
4. JDBC URL: ``jdbc:h2:mem:testdb``


Please not that the date format for sending a POST request to retrive avaliable Resturant is ``yyyy-MM-ddTHH:mm:ss.SSSZ``

Example request payload:

**Get Open Resturants By Date**

![alt adidas](https://github.com/Nnadozieomeonu/images/blob/46b8029c28ea859e60acbfc40058ade681de9a39/solution2.png?raw=true)

*https://tds-interview.herokuapp.com/tds/api/v1/resturant/avaliability*

*POST METHOD*

```json

{
  "date" : "2021-10-14T11:24:54.385Z"
}

```

See API documentation for more details https://tds-interview.herokuapp.com/swagger-ui.html

**Foo Bar Solution **

![alt adidas](https://github.com/Nnadozieomeonu/images/blob/2529bfddd71dee0216d273c529556a449acb8786/solution1.png?raw=true)

*https://tds-interview.herokuapp.com/tds/api/v1/

*GET METHOD*