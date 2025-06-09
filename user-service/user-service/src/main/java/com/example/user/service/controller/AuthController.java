package com.example.user.service.controller;

import com.example.user.service.dto.AuthRequest;
import com.example.user.service.dto.AuthResponse;
import com.example.user.service.dto.Email;
import com.example.user.service.feign.Notification;
import com.example.user.service.model.Role;
import com.example.user.service.model.User;
import com.example.user.service.security.CustomUserDetails;
import com.example.user.service.jwt.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  AuthService service;

    @Autowired
    private Notification notify;

    public AuthController(AuthenticationManager authenticationManager, AuthService service) {
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if (authentication.isAuthenticated()) {

                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                String role = userDetails.getUser().getRole().name();

                Email email = new Email();
                email.setTo(userDetails.getUser().getEmail());
                email.setSubject("Login Notification");
                email.setBody("Dear " + userDetails.getUser().getName() + ",\n\n" +
                        "We noticed a successful login to your account.\n" +
                        "If this wasn't you, please secure your account immediately.\n\n" +
                        "Thanks,\nTeam");

                try {
                    String response = notify.message(email);
                    System.out.println("Login Email response: " + response);
                } catch (Exception e) {
                    System.err.println("Error sending login email: " + e.getMessage());
                    e.printStackTrace();
                }

                return ResponseEntity.ok(new AuthResponse( "Login successful",userDetails.getUser().getRole().name(), service.generateToken(request.getEmail(),role)));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse( "Invalid credentials",null,null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid credentials",null,null));
    }

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {

        String service1 = service.saveUser(user);

        if (service1 != null) {
            Email email = new Email();
            email.setTo(user.getEmail());
            email.setSubject("Registration Successful");
            email.setBody("Dear " + user.getName() + ",\n\n" +
                    "Welcome to our platform! Your registration is successful.\n" +
                    "Feel free to explore and use our services.\n\n" +
                    "Thanks,\nTeam");

            try {
                String response = notify.message(email);
                System.out.println("Email response: " + response);
            } catch (Exception e) {
                System.err.println("Error sending email: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(service1, HttpStatus.CREATED);
    }


    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
