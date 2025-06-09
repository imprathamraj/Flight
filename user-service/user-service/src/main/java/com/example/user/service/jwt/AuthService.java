package com.example.user.service.jwt;

import com.example.user.service.model.User;
import com.example.user.service.repository.UserRepository;
import com.example.user.service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository repository;

    private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder(12);

    @Autowired
    JwtService jwtService;

    public String saveUser(User credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(String email,String role) {
        return jwtService.generateToken(email,role);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}