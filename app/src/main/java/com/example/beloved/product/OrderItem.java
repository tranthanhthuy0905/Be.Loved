package com.example.beloved.product;

import android.os.Parcel;
import android.os.Parcelable;

// OrderItem.java
public class OrderItem implements Parcelable {
    private final int imageResource;
    private final String productName;
    private final String productPrice;

    public OrderItem(int imageResource, String productName, String productPrice) {
        this.imageResource = imageResource;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getImageResource() {return imageResource;}

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    protected OrderItem(Parcel in) {
        imageResource = in.readInt();
        productName = in.readString();
        productPrice = in.readString();
    }
    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeString(productName);
        dest.writeString(productPrice);
    }
}
