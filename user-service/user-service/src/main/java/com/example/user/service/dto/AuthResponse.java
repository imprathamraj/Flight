package com.example.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String role;
    private String token;

    public AuthResponse(String message,String role,String token){
        this.message=message;
        this.role=role;
        this.token=token;
    }

}
