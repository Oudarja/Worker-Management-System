/*
 * The JWTAuthFilter.java file in the config folder of your project
 * is likely a filter that intercepts incoming HTTP requests to verify
 * JWTs (JSON Web Tokens) before granting access to protected resources.
 * In a Spring Security setup, this filter plays a crucial role in implementing
 * token-based authentication.
 * 
 * The provided JWTAuthFilter implementation is a filter in the Spring Security 
 * framework used to validate JWTs (JSON Web Tokens) in incoming HTTP requests 
 * and authenticate users if the token is valid.
 */

package com.example.UserManagementSystem.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.UserManagementSystem.service.JWTUtils;
import com.example.UserManagementSystem.service.OurUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    // Marking the field as private ensures that the field is only accessible within
    // the class.
    // This enforces better encapsulation, a key principle of Object-Oriented
    // Programming, ensuring
    // other classes cannot directly modify or access the jwtUtils field.

    /*
     * The @Autowired annotation in Spring is used for Dependency Injection (DI). It
     * tells Spring
     * to automatically provide or inject a required dependency (i.e., an object
     * instance) into
     * the marked field, constructor, or method.
     */

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Fetching the Authorization header from the HTTP request.
        /*
         * Authorization Header retrieves:
         * 1)Every HTTP request contains headers—key-value pairs providing metadata
         * about
         * the request.
         * 2)The Authorization header is often used to include credentials for
         * authentication purposes
         * (e.g., JWT, Basic Auth tokens, or API keys).
         * 
         * Example of an Authorization header in a request:
         * Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR...
         * 
         * A bearer token is a security token that grants the bearer (whoever has it)
         * access to specific resources.
         * The term "bearer" means "whoever bears or possesses the token" can use it to
         * authenticate themselves.
         */

        final String authHeader = request.getHeader("Authorization");

        final String jwtToken;
        final String userEmail;

        /*
         * 
         * if (authHeader == null || authHeader.isBlank()) {
         * filterChain.doFilter(request, response);
         * return;
         * }
         * If the header is null or blank, the filter passes the
         * request down to the next filter in the chain.
         * This essentially means, "Skip processing JWT validation
         * in this filter" if there is no valid Authorization header.
         * 
         */

        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * Skips the "Bearer " part (7 characters) from the Authorization
         * header.Extracts only the actual JWT token for further processing.
         */

        jwtToken = authHeader.substring(7);

        // Decodes the JWT and retrieves the username (or email, depending on what is
        // stored as the subject in the token).
        // Internally, this uses methods like Jwts.parser() and parses the Claims in the
        // token.

        userEmail = jwtUtils.extractUsername(jwtToken);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Queries the database or repository (UsersRepo) to fetch the user with the
            // corresponding
            // email or username from the token.
            UserDetails userDetails = ourUserDetailsService.loadUserByUsername(userEmail);

            // Ensures the token's signature matches and is not expired.
            // Also ensures the username in the token matches the user's username.

            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // If the token is valid, a UsernamePasswordAuthenticationToken is created to
                // represent the user's authenticated state.
                // userDetails: Contains the user's username, password, and granted authorities.
                // null: Indicates no credentials are being passed during authentication (JWT
                // tokens are stateless).
                // userDetails.getAuthorities(): Fetches the user's roles or permissions.

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // WebAuthenticationDetailsSource is a Spring class that extracts contextual
                // details about the request.
                // Builds and retrieves a WebAuthenticationDetails object that includes:
                // The remote IP address (client's IP address sending the request).
                // The session ID (if any).
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /*
                 * SecurityContext:
                 * A container used by Spring Security to store the Authentication object for
                 * the
                 * currently authenticated user during a request.
                 */
                securityContext.setAuthentication(token);
                /*
                 * Replaces the existing (empty) security context with the newly populated one
                 * that contains the authentication information.
                 * Now, Spring Security considers this request authenticated.
                 */
                SecurityContextHolder.setContext(securityContext);
            }
        }

        filterChain.doFilter(request, response);

    }

}
