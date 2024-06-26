# Chat Server System

## Overview
This project is a Chat Server system built using Spring Boot. 
It supports real-time communication between users, authentication and more

## Render Deployment 

- This application has been deployed with Render using Docker Image, you can access this application

  [Application Login URL](https://chat-server-2-n70y.onrender.com/login)

  [Application Swagger URL](https://chat-server-2-n70y.onrender.com/swagger-ui/index.html)
  
  [Application Logout URL](https://chat-server-2-n70y.onrender.com/logout)

## Prerequisites
- Java 17 or higher
- Maven
- IDE (e.g., IntelliJ, Eclipse)
- Lombok
- Git

## Setup

- Java 17 or higher is required to run the application
- Git Clone
- mvn clean install
- Make sure you have lombok in Eclipse IDE or you can use IntelliJ
- navigate to the ChatServerApplication.java file, Run as Java application
- Launch the Application in the browser: [URL](http://localhost:8080/)
- once the application launches, it prompts to enter username and password, you can find it under [SQL File](https://github.com/abdulwhd964/chat-server/blob/main/src/main/resources/schema.sql)
    
## Technologies Used
- Spring Boot
- Spring Security
- WebSocket
- JPA/Hibernate
- H2 Database (configurable for other databases)
- Maven
- AOP
- Spring Cache
- Implemented with ELK
- Docker
- Swagger for documentation

## Features


### 1. WebSockets

- **Connect to WebSocket
- **Endpoint: /ws
- **Subscribing to Messages: /topic/messages
- **Subscribing to Deleted Messages: /topic/delete/messages
- **Sending a Message: /app/chat
- **Deleting a Message: /app/delete


### 2. Operations

- **Post message:** [Post an message](http://localhost:8080/api/chat/message) , Method: POST
- **Get all messages:** [Get all messages](http://localhost:8080/api/chat/messages) , Method: GET
- **Get all messages by sender:** [Get all messages by sender](http://localhost:8080/api/chat/messages/sender) , Method: GET
- **Delete message by sender:** [Delete message by id and sender](http://localhost:8080/api/chat/message/1?username=abdul) , Method: Delete

### 3. Validation

- content: should not be empty

### 4. Logging

- Logging has been handled by SLF4J and also implemenented ELK Stack to view the logs
- logstash.conf file available at [Logstash file location](https://github.com/abdulwhd964/chat-server/blob/main/src/main/resources/logstash.conf)
- Use this: if you have ELK Setup : [Kibana URL](http://localhost:5601)
- Local Kibana Logs Image ![Alt text](https://github.com/abdulwhd964/chat-server/assets/61117499/6a456ff7-e2bd-4ec0-bd81-b6dd448edf97)

### 5. Transactional

- @Transactional has been handled incase of exception it will rollback the transaction

### 6. Swagger Documentation

- Access the Swagger documentation for the API at [Swagger URL](http://localhost:8080/swagger-ui/index.html)
- you can pass username and password in the Authorize tab, below image for reference ![Alt text](https://github.com/abdulwhd964/chat-server/assets/61117499/3c0916a2-2370-4ca9-9b0e-df21bf3061cc)

### 7. Caching

- Spring caching has been implemenated to improve the performance by storing the message

### 9. Builder Pattern

- The builder pattern allows you to create a fluent API for constructing objects. This results in more readable
  and expressive code when creating response objects, making it easier to understand the intent of the code.

### 10. Controller Tests

- Test case has been written for all the api and also failure case been implemented

### 11. Request Logging

- using CommonsRequestLoggingFilter, the application has been designed to print all the incoming requests

### 12. AOP with Before

- AOP has been implemenented to check the execution for each api (@Before)

### 13. Exception Handler

- We have implemented a global exception handler using @RestControllerAdvice and @ExceptionHandler annotations to handle exceptions thrown by controllers across the application.

### 14. Inmemory Database

- H2 Database URL: [H2-Console](http://localhost:8080/h2-console)
- Database: chatserverdb
- username: sa
- password: password

### 15. Deployment with Docker

- cd your-project-directory
    - Build Docker Image: Navigate to the project directory and build the Docker image using the provided Dockerfile. <br>
    - Build Command:
        - docker build -t chat-server .
    - Run Docker Container: Once the image is built, you can run it using Docker.<br>
    - Run Command:
        - docker run -p 8080:8080 chat-server .

### 16. [Postman collection](https://github.com/abdulwhd964/chat-server/files/15370116/Chat.Server.Api.postman_collection.json)


### 17. Dependencies

- springboot 3
- spring security
- spring-web
- lombok
- h2
- data-jpa
- actuator
- devtools
- mapstruct
- junit
- mockito
- swagger
- aop
- Java 17
- websockets