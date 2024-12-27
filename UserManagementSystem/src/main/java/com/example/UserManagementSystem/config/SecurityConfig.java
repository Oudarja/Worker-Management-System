/*
 * The SecurityConfig class is a Spring Security configuration class that
 * customizes the behavior of security settings such as user authentication, endpoint 
 * authorization, session management, CSRF handling, CORS, and JWT filtering.
 */

package com.example.UserManagementSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import com.example.UserManagementSystem.service.OurUserDetailsService;

//@Configuration:Marks this class as a configuration class for Spring.
//Spring will pick it up automatically during component scanning.

/*@EnableWebSecurity
 * Enables Spring Security in the application.
   It tells Spring to apply security rules to HTTP requests.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     * OurUserDetailsService: A custom implementation of Spring Security's
     * UserDetailsService, responsible for loading user-specific data from a
     * database (or other storage).
     */

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    // JWTAuthFilter: A custom filter class that intercepts requests to validate JWT
    // tokens.

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    // This method configures Spring Security's behavior for
    // HTTP requests.
    /*
     * The securityFilterChain(HttpSecurity httpSecurity) method customizes how HTTP
     * requests are secured
     */
    /*
     * CSRF (Cross-Site Request Forgery) protection is disabled since the app uses
     * stateless JWT authentication.
     * For stateless applications, CSRF protection is unnecessary because JWT tokens
     * handle authentication without sessions.
     * 
     * cors(Customizer.withDefaults()):
     * 1)CORS (Cross-Origin Resource Sharing) is enabled with default settings.
     * 2)Allows APIs to be accessed from other origins (e.g., other domains or
     * ports).
     * 3)Customizer.withDefaults() ensures Spring uses default CORS configurations.
     * 
     * Authorization Rules for HTTP Requests:
     * 
     * 1)permitAll(): Public endpoints like /auth/** and /public/** are accessible
     * to everyone.
     * 
     * 2)hasAnyAuthority("ADMIN"): Endpoints under /admin/** require users to have
     * the "ADMIN" authority.
     * 
     * 3)hasAnyAuthority("USER"): /user/** can be accessed by users with the "USER"
     * authority.
     * 
     * 4)hasAnyAuthority("ADMIN", "USER"): /adminuser/** can be accessed by users
     * with either "ADMIN" or "USER" roles.
     * 
     * 5)anyRequest().authenticated(): All other endpoints require authentication.
     * 
     * Configures the application as stateless
     * 
     * .sessionManagement(manager ->
     * manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)):
     * Configures the application as stateless.
     * Stateless applications do not store session information; JWT tokens
     * authenticate each request.
     * SessionCreationPolicy.STATELESS: Disables session creation.
     * 
     * 
     * JWT Filter (jwtAuthFilter) is placed before Spring's default
     * UsernamePasswordAuthenticationFilter.The JWT filter validates the JWT
     * tokens before processing authentication logic.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**", "/public/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/user/**").hasAnyAuthority("USER")
                        .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN", "USER")
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    // This method defines an AuthenticationProvider that is responsible for
    // authenticating users
    // based on username and password.

    // It uses a data source (via UserDetailsService) to fetch user credentials
    // during authentication.
    // Dao stands for Data Access Object.

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider is a built-in Spring Security implementation of
        // AuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // A custom service that loads user information (username, password, roles,
        // etc.) from
        // the database or any other source.
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        // Encrypts passwords securely and verifies that the raw password provided
        // matches the hashed password stored in the database.
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // Encrypts passwords securely and verifies that the raw password provided
    // matches
    // the hashed password stored in the database.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * AuthenticationManager is part of Spring Security’s authentication
     * architecture.
     * It is the main interface for handling authentication requests.
     * It is responsible for delegating the authentication process to one or more
     * AuthenticationProviders to verify user credentials.
     */
    /*
     * It uses the AuthenticationConfiguration to get the default
     * AuthenticationManager provided by Spring Security, which knows
     * how to handle authentication based on your configuration
     */
    /*
     * This method allows you to:
     * Access the AuthenticationManager bean in other parts of your
     * application, such as controllers or services.
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
