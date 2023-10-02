package com.example.fitzone;

public class UserItem {
    private String userName;
    private int userImageResourceId;

    public UserItem(String userName, int userImageResourceId) {
        this.userName = userName;
        this.userImageResourceId = userImageResourceId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserImageResourceId() {
        return userImageResourceId;
    }
}
