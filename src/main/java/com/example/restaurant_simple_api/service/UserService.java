package com.example.restaurant_simple_api.service;


import com.example.restaurant_simple_api.model.User;
import com.example.restaurant_simple_api.repository.UserRepository;
import com.example.restaurant_simple_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final ImageService imageService;

    public UserService(@Lazy ImageService imageService) {
        this.imageService = imageService;
    }
    public String register(User user) {
        // Check if email or phone number already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email or phone number already in use.");
        }
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Generate JWT
        return jwtUtil.generateToken(user.getUserId());
    }

    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(userOptional.get().getUserId());
            } else {
                throw new RuntimeException("Invalid password.");
            }
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateProfile(Long userId, User userDetails, MultipartFile profilePic) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Update fields
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setLocation(userDetails.getLocation());

            // Handle profile picture upload
            if (profilePic != null && !profilePic.isEmpty()) {
                String fileName = imageService.uploadImage(profilePic);
                user.setProfilePic(fileName);
            }

            return userRepository.save(user);
        }
        return null;
    }

}
