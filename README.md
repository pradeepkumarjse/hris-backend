<<<<<<< HEAD
# locoldeal-backend
=======
# Sunbase Java Assignment - Customer Management System

This Spring Boot application serves as a Customer Management System, providing APIs for creating, updating, retrieving, and deleting customer records. JWT authentication is implemented to secure the APIs.

## Prerequisites

Before running the application, make sure you have the following:

- Java Development Kit (JDK) installed (version 8 or later)
- MySQL database installed and running
- Your MySQL database configuration details (username, password, etc.)

## Configuration

Configure the application by updating the `application.properties` file. Here are the key properties:

```properties
jwt.secret=sunbaseJavaAssignment

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/sunbase?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

# For API Documentation
springdoc.swagger-ui.url=/v3/api-docs
springdoc.swagger-ui.path=/api-doc.html
```

Update the `jwt.secret` with a secure secret for JWT token generation. Adjust database properties based on your MySQL configuration.

## Build and Run

To build and run the application, follow these steps:

1. Open a terminal and navigate to the project root directory.
2. Build and Run the project:

   This will start the Spring Boot application.

4. Once the application is running, you can access the APIs and the Swagger UI documentation:

   - **APIs:** http://localhost:8080/api/customers
   - **Swagger UI:** http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

## API Documentation

Explore the API documentation using Swagger UI, available at the following URL:

[Swagger UI](http://localhost:8080/api-doc.html](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

## Authentication

To authenticate further API calls, obtain a Bearer token by calling the authentication API. Here's a sample request:

```bash
curl -X POST http://localhost:8080/authenticate \
  -H "Content-Type: application/json" \
  -d '{"username": "your_username", "password": "your_password"}'
```

This will return a Bearer token. Include this token in the Authorization header for subsequent API calls:

```bash
curl -X GET http://localhost:8080/api/customers \
  -H "Authorization: Bearer your_token_here"
```



## Register your first user from Swagger UI

Go to http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/Authentication/saveUser

Call /register api to register your first user

{
  "username": "pradeep",
  "password": "pradeep",
  "email": "pradeep@pradeep.com",
  "firstname": "Pradeep",
  "lastname": "Kumar",
  "role": [
    {
      "id": 1,
      "roleName": "SITE_ADMIN",
      "description": "SITE_ADMIN"
    }
  ],
  "created_DATE": "2024-01-20",
  "last_MODIFIED_DATE": "2024-01-20"
}

use your regsitered username and password to login into frontend/APIs




![Project Screenshot 1](./project-screenshot-1.png)



# Frontend Repository URL - https://github.com/pradeepkumarjse/FrontendSunbaseJavaAssignment
>>>>>>> 0528bba6898f537b0ef4f6b12869d10fe49b6636
