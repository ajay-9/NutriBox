package com.example.tiffincart.Model;

public class ChefItem {
    private String name;
    private int imageResId;

    public ChefItem(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
