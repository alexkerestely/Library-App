# Library-App

Spring Boot application with Maven and Kotlin that uses microservices to simulate an library application with cache. 
Components:
 - interface which takes the web requests
 - main microservice which performs the operations and communicates with cache
 - cache microservice that stores queries and their results in a MYSQL database using Hibernate ORM
For inter-microservice communication RabbitMQ was used
