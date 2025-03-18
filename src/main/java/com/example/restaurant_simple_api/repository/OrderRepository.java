package com.example.restaurant_simple_api.repository;

import com.example.restaurant_simple_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(Long orderId);
    List<Order> findByUser_UserId(Long userId);
}