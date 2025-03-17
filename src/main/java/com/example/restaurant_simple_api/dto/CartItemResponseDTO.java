package com.example.restaurant_simple_api.dto;

public class CartItemResponseDTO {
    private Long cartId;
    private Long userId;
    private Long restaurantId;
    private Long itemId;
    private Integer quantity;
    private String itemName;
    private Integer itemPrice;
    private String description;
    private Integer totalPrice;
    private String itemImage;
    private Double currentOfferDiscountRate;

    public CartItemResponseDTO(Long cartId, Long userId, Long restaurantId, Long itemId, Integer quantity, String itemName, Integer itemPrice, String description, Integer totalPrice, String itemImage, Double currentOfferDiscountRate) {
        this.cartId = cartId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.description = description;
        this.totalPrice = totalPrice;
        this.itemImage = itemImage;
        this.currentOfferDiscountRate = currentOfferDiscountRate;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Double getCurrentOfferDiscountRate() {
        return currentOfferDiscountRate;
    }

    public void setCurrentOfferDiscountRate(Double currentOfferDiscountRate) {
        this.currentOfferDiscountRate = currentOfferDiscountRate;
    }
}