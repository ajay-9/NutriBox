package com.example.tiffincart.Model;

public class TiffinItem {
    private String name;
    private int imageResId;

    public TiffinItem(String name, int imageResId) {
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
