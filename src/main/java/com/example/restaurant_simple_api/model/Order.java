package com.example.restaurant_simple_api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "order_status", columnDefinition = "INTEGER DEFAULT 1")
    private Integer orderStatus = 1; // 1 = placed (default), 2 = preparing, 3 = out for delivery, 4 = delivered

    @Column(name = "payment_status", columnDefinition = "INTEGER DEFAULT 1")
    private Integer paymentStatus = 1; // 1 = pending (default), 2 = completed, 3 = failed

    @Column(name = "delivery_person_id")
    private Long deliveryPersonId; // Default is null

    @Column(name = "isAccept",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isAccept;

    @Column(name="pickupLocation")
    private String pickupLocation;  // Geography/Point type

    @Column(name="dropoffLocation")
    private String dropoffLocation; // Geography/Point type

    @Column(name="pickupCity")
    private String pickupCity;      // New field for pickup city

    @Column(name="dropoffCity")
    private String dropoffCity;     // New field for dropoff city

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartItems; // List of items in the order

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public void setDeliveryPersonId(Long deliveryPersonId) {
        this.deliveryPersonId = deliveryPersonId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Cart> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Cart> cartItems) {
        this.cartItems = cartItems;
    }

    public Boolean getAccept() {
        return isAccept;
    }

    public void setAccept(Boolean accept) {
        isAccept = accept;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public String getPickupCity() {
        return pickupCity;
    }

    public void setPickupCity(String pickupCity) {
        this.pickupCity = pickupCity;
    }

    public String getDropoffCity() {
        return dropoffCity;
    }

    public void setDropoffCity(String dropoffCity) {
        this.dropoffCity = dropoffCity;
    }
}
