package com.example.fitzone;

public class DietItem {
    private String dietName;
    private String imageUrl;
    private String dietDescription;
    private String dietFocusArea;

    public DietItem(String dietName, String dietImageResourceId) {
        this.dietName = dietName;
        this.dietDescription = dietImageResourceId;
    }

    public DietItem(String dietName) {
        this.dietName=dietName;
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

    public DietItem(String dietName, String dietDescription, String imageUrl) {
        this.dietName = dietName;
        this.dietDescription=dietDescription;
        this.imageUrl=imageUrl;
    }

    public String getDietName() {
        return dietName;
    }

    public String getDietImageResourceId() {
        return dietDescription;
    }

}
