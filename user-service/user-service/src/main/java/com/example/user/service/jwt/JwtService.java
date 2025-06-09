package com.example.user.service.jwt;


import com.example.user.service.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

//    public String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private String secretKey= "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

//    public JwtService(){
//
//        try {
//            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk= keyGen.generateKey();
//            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }

    public String generateToken(String email,String role){

        Map<String,Object> claims= new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60))
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
//        System.out.println(secretKey);
        byte[] keybytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybytes);
    }



    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
    }





    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}