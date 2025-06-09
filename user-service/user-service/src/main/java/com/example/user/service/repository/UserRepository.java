package com.example.user.service.repository;

import com.example.user.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Find user by email (used for login & authentication)
    Optional<User> findByEmail(String email);

//    // Check if email already exists
//    boolean existsByEmail(String email);
//
//    // Check if phone number already exists (optional)
//    boolean existsByPhoneNumber(String phoneNumber);
}