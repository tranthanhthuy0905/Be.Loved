package com.example.beloved.models;

public class User {
    private String username;
    private Long phoneNumber;
    private String location;
    private String email;

    public User(String username, Long phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String username, Long phoneNumber, String location, String email) {
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

    public Long getPhoneNumber() {
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

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
