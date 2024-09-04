## Library App

The project is a services application that simulates an library application with a cache mechanism. The user inputs a query to either find by author, name or publisher, or print data in a specified format. The application checks if it can be retrieved from cache database and if not it performs the operation needed and returns.

Components:
 - interface which takes the web requests
 - main microservice which performs the operations and communicates with cache
 - cache microservice that stores queries and their results in a MYSQL database using Hibernate ORM

Technology stack:
- Build with Spring Boot and Maven
- Written in Kotlin
- RabbitMQ for communication between services
- Thymeleaf for displaying the query results in browser
- Spring Data JPA used for cache repository

