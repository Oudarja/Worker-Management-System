package com.example.UserManagementSystem.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// To find any unimplemented method when any class implements or extends any
//interface or class , class name/interface name->rightclick-> source actions->
//unimplemented method 

//It represents a user entity in the database with fields like 
//id, email, name, password, city, and role.
@Entity
@Table(name = "ourusers")
@Data
public class OurUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String email;
    private String name;
    private String password;
    private String city;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    /*
     * Returns the username used to authenticate the user.
     * Here, it maps to the email field.
     * Spring Security uses this for logging in.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /*
     * Returns true if the account is not expired.
     * Expiration refers to when a user account is considered
     * invalid after a specific time.
     * 
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // @Override
    // public boolean isCredentialsNonExpired() {
    // return UserDetails.super.isCredentialsNonExpired();
    // }

    /*
     * Returns true if the user is enabled.
     * Disabled accounts might be temporarily deactivated by an admin.
     * Why true? This setup assumes all users are enabled. In production,
     * you might check a boolean field like isEnabled.
     */

    @Override
    public boolean isEnabled() {

        return true;
    }

}

/*
 * It implements the UserDetails interface, which is a core part of
 * Spring Security.By implementing UserDetails, this class allows Spring
 * Security to retrieve user data for authentication and authorization.
 */

/*
 * Overall Role in the Application of this class
 * 
 * Authentication: When a user logs in, Spring Security loads the user details
 * via a UserDetailsService, which typically retrieves this OurUsers object
 * from the database based on the username (email in this case).
 * 
 * Authorization: Spring Security uses the authorities from the getAuthorities()
 * method to check the user's roles/permissions.
 * 
 */