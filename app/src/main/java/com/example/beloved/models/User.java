package com.example.beloved.models;

public class User {
    private String name;
    private String phoneNumber;
    private String location;
    private String email;
    private String uid;
    private String fcmToken;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User() {
    }

    public User(String username, String phoneNumber, String email) {
        this.name = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String username, String phoneNumber, String location, String email) {
        this.name = username;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.email = email;
    }

    public User(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
