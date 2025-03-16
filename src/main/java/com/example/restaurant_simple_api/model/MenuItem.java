package com.example.restaurant_simple_api.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "item_image")
    private String itemImage;

    @Column(name = "is_veg", nullable = false)
    private int isVeg; // 1 for veg, 2 for non-veg

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 1")
    private int status; // Status to indicate availability (e.g., 1 for active, 0 for inactive)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Stores the creation timestamp

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false, unique = true)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, unique = true)
    private Category category;

    // Getters and setters

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public int getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(int isVeg) {
        this.isVeg = isVeg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
