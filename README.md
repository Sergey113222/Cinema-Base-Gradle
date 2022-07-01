# Cinema Base

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Sergey113222_Cinema-Base-Gradle&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Sergey113222_Cinema-Base-Gradle)

- ## Introduction
    - Java application for search movies (by name, popular, last), view movie description and save your favourite movies
      with comment and ratting
    - Movies are searched using API https://www.themoviedb.org,
      documentation https://developers.themoviedb.org/3/getting-started/introduction
- ## Technologies
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
- ## Launch    
    - Use Docker-compose
        1. In application.properties file(sender microservice) fill username and password fields gmail 
        2. Build project
        3. Execute command 'docker-compose up -d' in terminal
        4. Script build backed images, database images, broker images, elk stack images and UI image. Then start
           containers with profile 'docker'

- ## Content
    - Module common contain model, dto, exception classes
    - Module dao execute repository layer with different profiles
        - JPA - implementing repository with Hibernate
        - JDBC - implementing repository with NameJdbcTemplate
    - Module database launch Liquibase for create tables into DB and insert values required data
    - Module generator. There are several profiles (jaxb, xstream) for convert (marshal/unmarshal) from DB to XML file
    - Module service (service layer application)
    - Module rest (rest controller layer)
    - Module security. Microservice for login which check user credentials and return jwt token
    - Module sender. Microservice for notification. Receive the most relevant information from broker and send email to
       user
    - Module web. User Interface (frontend)