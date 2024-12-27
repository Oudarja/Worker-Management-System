package com.example.UserManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserManagementSystem.entity.OurUsers;

public interface UsersRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);

}
