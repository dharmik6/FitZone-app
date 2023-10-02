package com.example.fitzone;

public class DietItem {
    private String dietName;
    private int dietImageResourceId;

    public DietItem(String dietName, int dietImageResourceId) {
        this.dietName = dietName;
        this.dietImageResourceId = dietImageResourceId;
    }

    public String getDietName() {
        return dietName;
    }

    public int getDietImageResourceId() {
        return dietImageResourceId;
    }
}
