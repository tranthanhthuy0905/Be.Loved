package com.example.beloved.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Post {
    private String title;
    private String description;
    private String created_at;
    private String price;
    private String status;
    private String image_url;
    private String key;

    Post(){}
    public Post(String title, String description, String image_url, String price, String status) {
        this.title = title;
        this.description = description;
        this.created_at = created_at();
        this.image_url = image_url;
        this.price = price;
        this.status = status;
    }

    private String created_at() {
        Date currentDate = new Date();

        // Format the date and time using a specific pattern
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(currentDate);
        return formattedDateTime;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public String getStatus() {
        return this.status;
    }

    public String getPrice() {
        return this.price;
    }
    public String getCreatedAt() {
        return this.created_at;
    }
    public void setCreated_at(String t) {
        this.created_at = t;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }


}
