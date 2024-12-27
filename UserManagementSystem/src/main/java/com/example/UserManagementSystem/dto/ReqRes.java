
/*
The ReqRes class in your dto folder serves as a 
Data Transfer Object (DTO). Its purpose is to 
encapsulate the data that will be exchanged between
different layers of your application, such as controllers
and services, or between your application and external systems

*/

package com.example.UserManagementSystem.dto;

import java.util.List;

import com.example.UserManagementSystem.entity.OurUsers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
//  Ensures that only non-null fields are included in the 
//JSON serialization, keeping the JSON response concise.

//@Data (Lombok):Automatically generates getters, setters, toString, hashCode, 
//and equals methods, reducing boilerplate code.
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {
    private int statusCode;
    private String error;
    private String message;

    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private String role;
    private String email;
    private String password;
    private OurUsers ourUsers;
    private List<OurUsers> ourUsersList;

}

/*
 * The fields of the class capture a combination of request and response
 * information (ReqRes appears to act as both a request and response object):
 * 
 * 1)Response-related Fields:
 * statusCode: Status code of the operation (e.g., HTTP status).
 * error: Details of any error, if applicable.
 * message: Additional information or success/error messages.
 * 
 * 2)Authentication-related Fields:
 * 
 * token and refreshToken: Authentication tokens (useful for login sessions).
 * expirationTime: Token expiration time (e.g., for JWT).
 * 
 * 3)User Information:
 * name, city, role, email, pasword: User information that may be returned
 * as part of a response or populated in a request.
 * OurUsers and List<OurUsers>: Allows for mapping a single user or a list
 * of users.
 * 
 * 
 */