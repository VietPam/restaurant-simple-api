package com.example.restaurant_simple_api.controller;

import com.example.restaurant_simple_api.service.CartService;
import com.example.restaurant_simple_api.util.ApiResponse;
import com.example.restaurant_simple_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long extractUserIdFromToken(String token) {
        try {
            return jwtUtil.extractUserId(token);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<ApiResponse> addToCart(@RequestHeader("Authorization") String token,
                                                 @RequestBody Map<String, Object> requestBody) {
        Long userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid or missing token.", null, false));
        }

        try {
            Long restaurantId = ((Number) requestBody.get("restaurantId")).longValue();
            Long itemId = ((Number) requestBody.get("itemId")).longValue();
            Integer quantity = (Integer) requestBody.get("quantity");

            ApiResponse response = cartService.addToCart(userId, restaurantId, itemId, quantity);
            return response.isStatus() ? ResponseEntity.ok(response) : ResponseEntity.status(409).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while adding to the cart.", null, false));
        }
    }

}
