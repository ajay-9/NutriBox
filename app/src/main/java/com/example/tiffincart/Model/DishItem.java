package com.example.tiffincart.Model;

public class DishItem {
    private String name;
    private String description;
    private String price;
    private String imageUrl;
    private String dishImage;

    public DishItem(String dishId, String dishName, String dishPrice, String dishImage) {
        // Required for Firebase
        this.dishImage = dishImage;
    }

    public DishItem(String name, String description, String price, String imageUrl, String dishImage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.dishImage = dishImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }
}
