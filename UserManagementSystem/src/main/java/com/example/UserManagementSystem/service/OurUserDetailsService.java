package com.example.UserManagementSystem.service;

import com.example.UserManagementSystem.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// The @Service annotation declares this class as a Spring-managed service. 
// The UserDetailsService interface is a Spring Security interface.
//Its purpose is to locate a user by their username (or any unique identifier).

@Service
public class OurUserDetailsService implements UserDetailsService {

    /*
     * The UsersRepo is a Spring Data JPA repository. It is used to
     * retrieve user data from the database.
     * The @Autowired annotation injects an instance of the repository
     * to access its methods.
     */

    @Autowired
    private UsersRepo usersRepo;
    /*
     * What It Does??
     * This method retrieves the user's details from the database using the provided
     * username.
     * In this case, it searches for the user in the database by email
     * (findByEmail()).
     * How It Works???
     * Spring calls this method when a user tries to log in.
     * The username passed to this method is extracted from the client’s login
     * request (e.g., from a login API endpoint).
     * 
     * Returns a UserDetails Object:
     * The UserDetails interface contains user authentication data (e.g., username,
     * password, roles).
     * Your entity class OurUsers implements UserDetails. Hence, the object returned
     * here provides all necessary user data for authentication.
     * 
     * 
     * 
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepo.findByEmail(username).orElseThrow();
    }
}

/*
 * How It Fits Into Spring Security
 * 1)Authentication Process:
 * Spring Security invokes loadUserByUsername() during the authentication
 * process.
 * This happens when a user tries to log in by providing their credentials.
 * 
 * 2)Token Creation:After fetching the user (from the database), the service
 * class
 * provides user data to the JWTUtils class to generate a token (via
 * generateToken()
 * or similar methods).
 * 
 * 3) Validating Tokens:
 * When a token is presented in subsequent requests, the username is extracted
 * (using
 * extractUsername() from JWTUtils), and loadUserByUsername() verifies that the
 * user
 * exists and is valid.
 * 
 * 
 * 
 */