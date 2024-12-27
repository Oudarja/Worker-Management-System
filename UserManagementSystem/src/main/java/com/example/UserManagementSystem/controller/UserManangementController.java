/*
 * The UserManagementController class in your Java code serves as the API controller 
 * for handling user-related operations. It provides endpoints to register, login, manage
 * user data, and perform administrative tasks like fetching all users, updating, or deleting 
 * user information.
 */

package com.example.UserManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserManagementSystem.dto.ReqRes;
import com.example.UserManagementSystem.entity.OurUsers;
import com.example.UserManagementSystem.service.UserManagementService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/*
 Marks this class as a Spring MVC Controller that handles HTTP requests
and returns responses as JSON.All methods return ResponseEntity<ReqRes>
objects, encapsulating the HTTP response status and body.
 */

@RestController
public class UserManangementController {

    @Autowired
    private UserManagementService userManagementService;

    /*
     * Endpoint: /auth/register
     * HTTP Method: POST
     * Request Body: A JSON object representing the user's registration
     * details (mapped to ReqRes).
     * Functionality: Calls register() in the service layer to save the
     * user's data. Returns a response with the registration status and a message.
     */

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes req) {
        return ResponseEntity.ok(userManagementService.register(req));
    }
    /*
     * Endpoint: /auth/login
     * HTTP Method: POST
     * Request Body: A JSON object containing login credentials (email and
     * password).
     * Functionality: Calls login() in the service layer, which validates the user's
     * credentials, generates tokens, and returns them.
     */

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(userManagementService.login(req));
    }
    /*
     * HTTP Method:POST (Typically used to submit data or perform actions that may
     * modify resources, such as refreshing a token.)
     * 
     * URL: /auth/refresh
     * Clients will send a request to this endpoint to refresh an expired or
     * about-to-expire authentication token.
     * 
     * @RequestBody ReqRes req:
     * The @RequestBody annotation maps the incoming JSON payload to the ReqRes
     * object.
     * This object is expected to contain a valid refresh token (req.getToken()).
     * 
     * Operation:
     * It delegates the logic of refreshing a token to the
     * UserManagementService.refreshToken() method.
     * Returns the output of that service method wrapped inside a ResponseEntity.
     */

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(userManagementService.refreshToken(req));
    }

    /*
     * Endpoint: /admin/get-all-users
     * HTTP Method: GET
     * Functionality: Calls getAllUsers() in the service layer to fetch all users
     * from the database. Returns a list of users or a "not found" message if the
     * database is empty.
     */

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    /*
     * Endpoint: /admin/get-users/{userId}
     * HTTP Method: GET
     * Path Variable: userId (ID of the user to fetch).
     * Functionality: Calls getUsersById() in the service layer to retrieve a
     * specific user by ID. Returns the user details or an error message if the
     * user doesn't exist.
     */

    // here passed parameter userId has to be passed to service function
    @GetMapping("/admin/get-user/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId) {
        return ResponseEntity.ok(userManagementService.getUsersById(userId));
    }

    /*
     * Endpoint: /admin/update/{userId}
     * HTTP Method: PUT
     * Path Variable: userId (ID of the user to update).
     * Request Body: Updated user details in the OurUsers object.
     * Functionality: Calls updateUser() in the service layer to update user fields
     * (e.g., email, name, password). Encodes the password if updated.
     */

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres) {
        return ResponseEntity.ok(userManagementService.updateUser(userId, reqres));
    }

    /*
     * Endpoint: /adminuser/get-profile
     * HTTP Method: GET
     * Authentication:Uses the SecurityContextHolder to get the currently
     * logged-in user's email.
     * Functionality:Fetches the user's information using getMyInfo() in the service
     * layer.Returns the profile details for the authenticated user.
     */

    // admin and user both can get profile

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = userManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /*
     * Endpoint: /admin/delete/{userId}
     * HTTP Method: DELETE
     * Path Variable: userId (ID of the user to delete).
     * Functionality: Calls deleteUser() in the service layer. Deletes the user if
     * found and returns a success or "not found" message.
     */

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId) {
        return ResponseEntity.ok(userManagementService.deleteUser(userId));
    }

}
