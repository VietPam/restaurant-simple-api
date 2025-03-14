package com.example.restaurant_simple_api.controller;

import com.example.restaurant_simple_api.model.User;
import com.example.restaurant_simple_api.service.UserService;
import com.example.restaurant_simple_api.util.ApiResponse;
import com.example.restaurant_simple_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

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


    @GetMapping("/get-profile")
    public ResponseEntity<ApiResponse> getProfile(@RequestHeader("Authorization") String token) {
        try {
            // Extract user ID from the token
            Long userId = jwtUtil.extractUserId(token);

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse("Invalid or missing token.", null, false));
            }

            // Retrieve user details
            User user = userService.getUserById(userId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("User not found.", null, false));
            }

            // Prepare the response with profile picture URL with simple null checks
            Map<String, Object> userDetails = new HashMap<>();

            if (user != null) {
                if (user.getFirstName() != null) userDetails.put("firstName", user.getFirstName());
                if (user.getLastName() != null) userDetails.put("lastName", user.getLastName());
                if (user.getEmail() != null) userDetails.put("email", user.getEmail());
                if (user.getPhoneNumber() != null) userDetails.put("phoneNumber", user.getPhoneNumber());
                if (user.getLocation() != null) userDetails.put("location", user.getLocation());
                if (user.getProfilePic() != null) userDetails.put("profilePic",  user.getProfilePic());
            }

            // Check if the userDetails map is populated, return response
            if (userDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("User details are incomplete or missing.", null, false));
            } else {
                return ResponseEntity.ok(new ApiResponse("Profile fetched successfully.", userDetails, true));
            }
        } catch (Exception e) {
            System.out.println("in get profile : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("An error occurred while fetching the profile.", null, false));
        }
    }

}
