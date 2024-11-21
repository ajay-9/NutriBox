package com.example.tiffincart.Model;

public class ConsultantItem {
    private String name;
    private int imageResId;

    public ConsultantItem(String name, int imageResId) {
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
