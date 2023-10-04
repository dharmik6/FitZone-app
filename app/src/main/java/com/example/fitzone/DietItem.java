package com.example.fitzone;

public class DietItem {
    private String dietName;
    private String dietDescription;
    private String imageUrl;

    public DietItem(String dietName, String dietDescription, String imageUrl) {
        this.dietName = dietName;
        this.dietDescription = dietDescription;
        this.imageUrl = imageUrl;
    }
    public DietItem(String dietName, String imageUrl) {
        this.dietName = dietName;
        this.imageUrl = imageUrl;
    }
    public String getDietName() {
        return dietName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
