## Time Data Security Ltd. [Documentation](https://tds-interview.herokuapp.com/swagger-ui.html) :thought_balloon:

![alt adidas](https://github.com/Nnadozieomeonu/images/blob/0d570b94ce1690df3174e14498b216adde548a01/Adam.gif?raw=true)

# Table of Contents :thought_balloon:
1. [Introduction and technology list](#Introduction-and-technology-list)
2. [Swagger API Documentation](https://tds-interview.herokuapp.com/swagger-ui.html).
3. [Problem Solving Question Solution](#Problem-Solving-Question-Solution)


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

The dependency manager used is Maven and you can find all the dependenies in the pox.xml file. also the CSV file has been copied and saved in the static directroy located in spring boot IOC resource directory, in this resource directory we can also find the application property files that holds the application configuration.

The Link to H2 Database is http://localhost:8080/h2-console
1. Database URL: ``http://localhost:8080/h2-console/login.jsp``
2. Username: ``sa``
3. Password: ``password``
4. JDBC URL: ``jdbc:h2:mem:testdb``

Heroku does not support local access to H2 DB so you can only be able to access db console via localhost, by cloning and setting up this project.

Please note that the date format for sending a POST request to retrive avaliable Resturant is ``yyyy-MM-ddTHH:mm:ss.SSSZ``

Example request payload:

**Get Open Resturants By Date**

![alt tds](https://github.com/Nnadozieomeonu/images/blob/46b8029c28ea859e60acbfc40058ade681de9a39/solution2.png?raw=true)

*https://tds-interview.herokuapp.com/tds/api/v1/resturant/avaliability*

Please note that the time zone is important  ``2021-10-15T11:00:54.385Z `` for the implementation to work of our Heuroku server it is  ``ss.SSSZ `` is  ``54.385Z``
which must be included. the date and time uses standard format.

*POST METHOD*

```json

{
  "date" : "2021-10-14T11:24:54.385Z"
}

```

See API documentation for more details https://tds-interview.herokuapp.com/swagger-ui.html

**Foo Bar Solution **

![alt tds](https://github.com/Nnadozieomeonu/images/blob/2529bfddd71dee0216d273c529556a449acb8786/solution1.png?raw=true)

*https://tds-interview.herokuapp.com/tds/api/v1/*

A live demo can be seen on the below url or [Click Here](https://tds-interview-web.herokuapp.com/) to access demo

*https://tds-interview-web.herokuapp.com/*

*GET METHOD*

![alt tds](https://github.com/Nnadozieomeonu/images/blob/90eb0615cb41e7853983e5c9afc25d2d3dc47ccf/demo.png?raw=true)




## Problem Solving Question Solution :thought_balloon:

**How many tennis balls fit into a Boeing 787 Dreamliner?**

First, we need to find the volume of the aeroplane and the tennis ball. 

Once we get these figures, we will divide the volume of the airplane with that of the tennis ball.

Estimating that Boeing 787 has an approximate radius of 1 meter 
(assuming the aeroplane has the same height of a normal office floor) 
and length ~33 meters (~400 seats in an aeroplane with 10 seats a row which 
gives us 40 rows and adding another 10 rows for pilot and toilets we get to 
~50 rows * 2 feet which gives us ~100 feet or ~33 meters).

The volume of aeroplane is 103m^3 or 33πm^3 or 33mπcm^3

Then, estimating that tennis ball has a radius 3.3cm we can calculate the volume.
In that case, the volume of the tennis ball can be calculated by the formula of a sphere.

Volume of a sphere is = (4 πr3)/3
The volume of the tennis ball would be ~47.916πcm^3.

Finally, after dividing the volume of the aeroplane by that to the tennis ball we arrive at
apportximate figure of tennis balls that can fit a Boeing 787 dreamliner.

Therefore, we can fit approximately 688,705 tennis balls in a Boeing 787

*Source  [City Investment Training](https://www.cityinvestmenttraining.com/post/how-many-tennis-balls-can-you-fit-in-a-boeing-747)*
