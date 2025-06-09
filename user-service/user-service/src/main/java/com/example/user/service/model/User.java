package com.example.user.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users") // Optional, defines table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId; // Primary Key

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email; // Unique Constraint

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Role-Based Authentication

}

