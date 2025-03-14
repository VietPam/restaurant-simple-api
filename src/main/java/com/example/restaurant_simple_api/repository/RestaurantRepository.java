package com.example.restaurant_simple_api.repository;

import com.example.restaurant_simple_api.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByEmail(String email);
    List<Restaurant> findByStatus(int status);

    Optional<Restaurant> findByEmailAndStatus(String email, int status);

}
