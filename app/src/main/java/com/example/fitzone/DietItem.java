package com.example.fitzone;

public class DietItem {
    private String dietName;
    private String imageUrl;
    private String dietDescription;

    public DietItem(String dietName, String imageUrl, String dietDescription) {
        this.dietName = dietName;
        this.imageUrl = imageUrl;
        this.dietDescription = dietDescription;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDietDescription() {
        return dietDescription;
    }

    public void setDietDescription(String dietDescription) {
        this.dietDescription = dietDescription;
    }
}
