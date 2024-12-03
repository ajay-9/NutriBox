package com.example.tiffincart.Model;

public class ConsultantItem {
    private String name;
    private int imageResId;
    private String profileInfo;

    public ConsultantItem(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public ConsultantItem(String name, int imageResId, String profileInfo) {
        this.name = name;
        this.imageResId = imageResId;
        this.profileInfo = profileInfo;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }
}
