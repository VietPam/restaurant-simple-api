package com.example.restaurant_simple_api.controller;

import com.example.restaurant_simple_api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<?> hello() {
        try {
            return ResponseEntity.ok("Hello World");
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Registration failed: " + e.getMessage(), null, false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}
