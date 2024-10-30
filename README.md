# Maybank Assessment

## Introduction
### Purpose
An application for maybank interview

### Scope
* This service is designed to fulfill the requirements of the Maybank assessment. 
* Please note that the APIs are intended for demonstration purposes, and some business flows may not fully align with typical use cases.

---
## Technologies
* Development
    * Language: `Java 21`
    * Framework: `Spring Boot 3`
    * Build tool: `Maven`
    * Others: `Lombok`, `Docker`, `Feign`, `openapi`

## How-to
### Set up DB
#### Introduction 
This guide will help you deploy a local database using Docker.

To start, run `docker-compose up` to launch the database locally. Once the database is up and running, you will need to **MANUALLY** create a database named **TESTDB**.

A wiremock server will be started together when running docker-compose up. If you don't want to run docker in your local. Please look for the wiremock folder and unzip it. 
Run the `run wiremock.bat`

### Run the application
Assuming you are using IntelliJ, run the following commands to clean and compile your project:

Open the terminal and execute:
```agsl
mvn clean compile
```

In IntelliJ, open the project and navigate to `/src/main/java/com/maybank/interview` and right-click on Main.java and select Run Main.main().
