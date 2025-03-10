package com.example.restaurant_simple_api.controller;

import com.example.restaurant_simple_api.model.User;
import com.example.restaurant_simple_api.service.UserService;
import com.example.restaurant_simple_api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {
            user.setUserId(null);
            String token = userService.register(user);
            ApiResponse response = new ApiResponse("Registration successful.", Map.of("token", token), true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Registration failed: " + e.getMessage(), null, false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        try {
            String token = userService.login(email, password);
            ApiResponse response = new ApiResponse("Login successful.", Map.of("token", token), true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Login failed: " + e.getMessage(), null, false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
