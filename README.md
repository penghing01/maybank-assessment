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

## Postman
Please find the postman collection from `src/docs/postman`. (In a real scenario, this wouldn't be located here; it's included here solely for quick sharing.)

### Description
The second request will send with the account holder name with `black`. Any account holder with name `black` will be rejected.
When creating account, a nested call will be triggered to check if the user is blacklisted with the account holder name. 
The request is sending to `http://localhost:9001`
The nested call is `mocked` with wiremock. Please run the wiremock locally with the `docker` or get it from `wiremock` folder to make sure it's working. 
Please refer section below for future information.

## How-to
### Set up DB
This guide will help you deploy a local database using Docker.

To start, run `docker-compose up` to launch the database locally. Once the database is up and running, you will need to **MANUALLY** create a database named **TESTDB**.

### Set up wiremock
A wiremock server will be started together when running docker-compose up. 
If you don't want to run docker in your local. Please look for the wiremock folder and unzip it. 
Double-click the `run wiremock.bat` to run it.

### Run the application
Assuming you are using IntelliJ, run the following commands to clean and compile your project:

Open the terminal and execute:
```
mvn clean compile
```

In IntelliJ, open the project and navigate to `/src/main/java/com/maybank/interview` and right-click on Main.java and select Run Main.main().

## Assessment

Q: Explore an api which will nested calling another api from 3rd party.

A: I’m using OpenFeign for this assessment. I’ve utilized WebClient or HttpClient in other projects.

Q: Each api required to log REQUEST & RESPONSE info into logs file.

A: I am using “logbook” for this. The following is one of the sample logs where type is referring it is an REQUEST or RESPONSE.  
```
{"type":"REQUEST","correlation":"0a3ff305-d19d-4443-8917-a1a701f510a7","method":"GET","uri":"http://localhost:9001/v1/blacklist?username=John Doe","host":"localhost","path":"/v1/blacklist","scheme":"http","port":"9001","headers":{"Accept":["application/json"]},"date":"Thu, 31 Oct 2024 17:54:02 MYT","client-info":"","request-context":"http://localhost:9001/v1/blacklist?username=John Doe","service":"maybank-assessment"}
{"type":"RESPONSE","correlation":"0a3ff305-d19d-4443-8917-a1a701f510a7","duration":358,"protocol":"HTTP/1.1","status":200,"headers":{"content-type":["application/json"],"matched-stub-id":["820aca56-e43b-47a1-bc0c-b8545526774d"],"transfer-encoding":["chunked"]},"body":{"username":"John Doe","isBlacklisted":false,"reason":"Not blacklisted"}}
```


