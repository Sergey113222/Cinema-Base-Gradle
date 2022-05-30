## Cinema Base

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Sergey113222_Cinema-Base-Gradle&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Sergey113222_Cinema-Base-Gradle)

- # Introduction
    - Java application for search movies (by name, popular, last), view movie description and save your favourite movies
      with comment and ratting
    - Movies are searched using API https://www.themoviedb.org,
      documentation https://developers.themoviedb.org/3/getting-started/introduction
- # Technologies
    - Java 11
    - SpringBoot 2.5.4
    - Gradle
    - Spring Data JPA (Hibernate, NamedJdbcTemplate)
    - Spring Security (Jwt)
    - String Test (JUnit, Mockito)
    - Spring Web (REST/SOAP/MVC)
    - MySql - production DB
    - MongoDB - additional DB
    - H2 - test DB
    - Liquibase
    - RabbitMQ, Kafka
    - Docker, Docker-compose
    - Jenkins
    - Checkstyle, Jacoco, Spotbug, SonarQube
    - ELK stack
    - Bootstrup
- # Launch
    - First way. Set up in several application.properties files
         fill username and password fields DB
         fill username and password fields gmail
         start every microservice manually
    - Second way. Use Docker-compose
         Build project
         Execute command 'docker-compose up -d' in terminal
         Script build backed images, database images, broker images, elk stack images and UI image. Then start
           containers with profile 'docker'

- # Content
    1. Module common contain model, dto, exception classes
    2. Module dao execute repository layer with different profiles
         JPA - implementing repository with Hibernate
         JDBC - implementing repository with NameJdbcTemplate
    3. Module database launch Liquibase for create tables into DB and insert values required data
    4. Module generator. There are several profiles (jaxb, xstream) for convert (marshal/unmarshal) from DB to XML file
    5. Module service (service layer application)
    6. Module rest (rest controller layer)
    7. Module security. Microservice for login which check user credentials and return jwt token
    8. Module sender. Microservice for notification. Receive the most relevant information from broker and send email to
       user
    9. Module web. User Interface (frontend)
