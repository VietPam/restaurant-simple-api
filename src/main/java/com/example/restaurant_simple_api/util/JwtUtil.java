package com.example.restaurant_simple_api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Generate a secure key for HS256
    private final SecretKey secretKey = Keys.hmacShaKeyFor("restaurantjwttokensecretkey123restaurantjwttokensecretkey123".getBytes(StandardCharsets.UTF_8));
    public String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();

        // Add only the user ID to the claims
        claims.put("userId", userId);

        return createToken(claims, String.valueOf(userId)); // Set subject as userId or any unique identifier
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS256) // Use the secure key
                .compact();
    }
}
