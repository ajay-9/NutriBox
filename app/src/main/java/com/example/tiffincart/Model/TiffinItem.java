package com.example.tiffincart.Model;

public class TiffinItem {
    private String name;
    private int imageResId;
    private String description;
    private String price;
    private String calories;
    private String ingredients;
    private String restaurant;
    private String rating;
    private String reviews;
    private String plan;
    private String specialInstructions;


    public TiffinItem(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public TiffinItem(String name, int imageResId, String description, String price, String calories, String ingredients, String restaurant) {
        this.name = name;
        this.imageResId = imageResId;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.ingredients = ingredients;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
