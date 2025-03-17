package com.example.restaurant_simple_api.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderedItemsResponse {
    private Long cartId;
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private String restaurantName;
    private Integer totalPrice;
    private LocalDateTime orderCreatedAt;
    private Integer orderStatus;
    private List<Map<String, Object>> orderedItems;  // List of ordered items as a map

    public OrderedItemsResponse(Long cartId, Long orderId, Long userId, Long restaurantId, String restaurantName, Integer totalPrice, LocalDateTime orderCreatedAt, Integer orderStatus, List<Map<String, Object>> orderedItems) {
        this.cartId = cartId;
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.totalPrice = totalPrice;
        this.orderCreatedAt = orderCreatedAt;
        this.orderStatus = orderStatus;
        this.orderedItems = orderedItems;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(LocalDateTime orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Map<String, Object>> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<Map<String, Object>> orderedItems) {
        this.orderedItems = orderedItems;
    }
}
