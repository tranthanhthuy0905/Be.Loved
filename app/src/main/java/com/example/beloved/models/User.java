package com.example.beloved.models;

public class User {
    private String username;
    private String phoneNumber;
    private String location;
    private String email;
    private String userId;
    private String fcmToken;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User() {
    }

    public User(String username, String phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String username, String phoneNumber, String location, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.email = email;
    }

    public User(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
