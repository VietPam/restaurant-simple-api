package com.example.restaurant_simple_api.util;

import io.jsonwebtoken.Claims;
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
    public Long extractUserId(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }
        System.out.println("token ================ "+ token);
        return extractClaim(token, claims -> Long.parseLong(claims.get("userId").toString()));
    }

    private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // Use the secure key
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

}
