package com.example.beloved.product;

// OrderItem.java
public class OrderItem {
    private final int imageResource;
    private final String productName;
    private final String productPrice;

    public OrderItem(int imageResource, String productName, String productPrice) {
        this.imageResource = imageResource;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }
}
