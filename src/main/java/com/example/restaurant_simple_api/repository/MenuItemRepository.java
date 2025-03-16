package com.example.restaurant_simple_api.repository;

import com.example.restaurant_simple_api.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@
        Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByStatus(Integer status);
    List<MenuItem> findByCategoryCategoryIdAndStatus(Long categoryId, int status);
    List<MenuItem> findByRestaurantRestaurantIdAndStatus(Long restaurantId, int status);
    List<MenuItem> findByRestaurantRestaurantIdAndStatusAndCategoryCategoryId(Long restaurantId, int status, Long categoryId);
    List<MenuItem> findByStatusAndNameContainingIgnoreCase(int status, String name);

}
