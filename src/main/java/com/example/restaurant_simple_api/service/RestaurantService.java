package com.example.restaurant_simple_api.service;
import com.example.restaurant_simple_api.model.Restaurant;
import com.example.restaurant_simple_api.repository.RestaurantRepository;
import com.example.restaurant_simple_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) {
        Optional<Restaurant> userOptional = restaurantRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Restaurant restaurant = userOptional.get();
            if (restaurant.getStatus() == 1) {
                if (passwordEncoder.matches(password, restaurant.getPassword())) {
                    return jwtUtil.generateToken(userOptional.get().getRestaurantId());
                } else {
                    throw new RuntimeException("Invalid Credentials !!");
                }
            }
            else
            {
                throw new RuntimeException("Restaurant not found or not eligible to log in.");
            }
        } else {
            throw new RuntimeException("Invalid Credentials !!");
        }
    }
}
