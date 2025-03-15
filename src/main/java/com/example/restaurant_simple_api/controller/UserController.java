package com.example.restaurant_simple_api.controller;

import com.example.restaurant_simple_api.model.User;
import com.example.restaurant_simple_api.service.UserService;
import com.example.restaurant_simple_api.util.ApiResponse;
import com.example.restaurant_simple_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("location") String location,
            @RequestParam(value = "profilePic", required = false) MultipartFile profilePic) {

        // Extract user ID from the token
        Long userId = jwtUtil.extractUserId(token);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid token", "data", null, "status", false));
        }

        // Create a new User object with updated details
        User userDetails = new User();
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setPhoneNumber(phoneNumber);
        userDetails.setLocation(location);

        // Update the user profile with the provided details and profile picture
        User updatedUser = userService.updateProfile(userId, userDetails, profilePic);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found", "data", null, "status", false));
        }

        // Check if profile picture is not null
        if (updatedUser.getProfilePic() != null) {
            String profilePicUrl = updatedUser.getProfilePic();
            updatedUser.setProfilePic(profilePicUrl);
        }

        return ResponseEntity.ok(Map.of("message", "User profile updated successfully", "data", updatedUser, "status", true));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        ApiResponse response = userService.sendOtp(email);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/verify-otp-and-update-password")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        ApiResponse response = userService.verifyOtpAndUpdatePassword(email, otp, newPassword);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> request) {
        String old_pass = request.get("oldPassword");
        String new_pass = request.get("newPassword");

        ApiResponse response = userService.changePassword(token, old_pass,new_pass);
        return ResponseEntity.status(response.isStatus() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
    }
}
