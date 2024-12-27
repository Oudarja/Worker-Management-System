//The CorsConfig class in your Spring Boot application is used to 
//configure CORS (Cross-Origin Resource Sharing) settings globally 
//for the application

/*
 * CORS is a security feature in browsers that restricts web pages 
 * from making requests to a different domain (origin) than the one
 * that served the web page.
   For example, if your backend API runs on http://localhost:8080 and
   your frontend runs on http://localhost:3000, browsers will block the
   frontend from accessing the backend unless CORS is explicitly allowed.
   The above configuration enables CORS requests for the application by 
   specifying which origins, methods, and mappings are allowed.
 */

package com.example.UserManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Marks this class as a configuration class in Spring.
//It will automatically be scanned by Spring Boot during startup.
@Configuration
public class CorsConfig {
    // It is an interface provided by Spring MVC to customize the
    // configuration of MVC-specific components.
    // In this case, you are overriding the addCorsMappings() method
    // to customize CORS behavior.

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {
            // The CorsRegistry object allows you to define CORS rules
            // for specific endpoints.
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                /// ** means apply these CORS rules to all endpoints
                /// in the application.
                /// Defines the HTTP methods (GET, POST, PUT, DELETE)
                /// that are allowed to be used in CORS requests.
                /// The allowedOrigins("*") allows requests from any
                /// origin/domain (wildcard).For production, this can
                /// be replaced with specific origins for better security
                /// (e.g., http://example.com).
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("http://localhost:3000");
            }
        };
    }

}
