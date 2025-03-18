package com.example.restaurant_simple_api.service;

import com.example.restaurant_simple_api.model.Cart;
import com.example.restaurant_simple_api.model.Order;
import com.example.restaurant_simple_api.model.Restaurant;
import com.example.restaurant_simple_api.model.User;
import com.example.restaurant_simple_api.repository.CartRepository;
import com.example.restaurant_simple_api.repository.OrderRepository;
import com.example.restaurant_simple_api.repository.RestaurantRepository;
import com.example.restaurant_simple_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class    OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    public Order createOrder(Long userId, Long restaurantId, Long amount, Long discount, String pickupLocation, String dropoffLocation, String pickupCity, String dropoffCity) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setAmount(amount);
        order.setDiscount(discount);
        order.setPickupLocation(pickupLocation);
        order.setDropoffLocation(dropoffLocation);
        order.setPickupCity(pickupCity);
        order.setDropoffCity(dropoffCity);
        order.setAccept(false);
        order.setPaymentStatus(0); // 0 = Pending, 1 = Paid

        order = orderRepository.save(order); // Persist the order in the database

        return order;
    }
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_UserId(userId);
    }

    public Order updateOrderStatus(Long orderId, int newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus(newStatus);
        order = orderRepository.save(order);

        List<Cart> cartItems = cartRepository.findByUser_UserIdAndRestaurant_RestaurantIdAndStatusAndOrderIsNull(order.getUser().getUserId(), order.getRestaurant().getRestaurantId(), 1);

        for (Cart cartItem : cartItems) {
            cartItem.setStatus(2);
            cartRepository.save(cartItem);
        }

        return order;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
