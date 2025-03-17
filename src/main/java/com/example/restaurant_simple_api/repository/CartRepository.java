package com.example.restaurant_simple_api.repository;

import com.example.restaurant_simple_api.model.Cart;
import org.springdoc.core.converters.models.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_UserIdAndStatus(Long userId, int status);
    Optional<Cart> findByUser_UserIdAndMenuItem_ItemIdAndStatus(Long userId, Long itemId, Integer status);
    List<Cart> findByUser_UserIdAndRestaurant_RestaurantIdAndStatusAndOrderIsNull(Long userId, Long restaurantId, Integer status);
    List<Cart> findByUserUserIdAndOrderIsNotNullAndOrderOrderStatus(Long userId, Integer orderStatus, Sort sort);
    List<Cart> findByUserUserIdAndOrderIsNotNullAndOrderOrderStatusNot(Long userId, Integer orderStatus, Sort sort);

}
