package com.example.UserManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Spring Framework supports various architectural patterns, and Spring MVC
is one of the most commonly used ones for building web applications. 
However, not all Spring applications inherently follow the MVC pattern. 

***A Spring application follows the MVC pattern if it exhibits the following:

1)Model:Represents the application's data and business logic.
Typically handled by entities, DTOs, or data models, which 
interact with a persistence layer (like a database) via repositories.

2)View:Refers to the UI that users interact with.In Spring MVC:
Common technologies include Thymeleaf, JSP, or any frontend served by
Spring Controllers.The view is configured via templating engines or @Controller
methods returning ModelAndView.

3)Controller:Manages user requests and mediates between the Model and the View.
In Spring MVC, controllers are annotated with @Controller or @RestController and
map HTTP requests to methods (via @RequestMapping, @GetMapping, etc.).

Instead of being part of the core MVC layers, the service layer[service folder] fits
in as a Business Logic Layer that supports the Controller and interacts with the Model/Repository.
*/

@SpringBootApplication
public class UserManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementSystemApplication.class, args);
	}

}

/*
 * {
 * "statusCode": 200,
 * "message": "Successfully Logged In",
 * "token":
 * "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdWRhcmphMzYwQGdtYWlsLmNvbSIsImlhdCI6MTczNDc4NjU4NSwiZXhwIjoxNzM0ODcyOTg1fQ.GqTm8hcVIxr_Im5sDE3ZyXlTBgw4L_YzvaFapXb_6DU",
 * "refreshToken":
 * "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvdWRhcmphMzYwQGdtYWlsLmNvbSIsImlhdCI6MTczNDc4NjU4NSwiZXhwIjoxNzM0ODcyOTg1fQ.GqTm8hcVIxr_Im5sDE3ZyXlTBgw4L_YzvaFapXb_6DU",
 * "expirationTime": "24Hrs",
 * "role": "ADMIN"
 * }
 * 
 * 
 */