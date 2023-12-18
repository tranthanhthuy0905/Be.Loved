package com.example.beloved.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.beloved.models.User;

public class AndroidUtil {

    public static  void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, User model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhoneNumber());
        intent.putExtra("userId",model.getUserId());
        intent.putExtra("fcmToken",model.getFcmToken());

    }

    public static User getUserModelFromIntent(Intent intent){
        User userModel = new User();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhoneNumber(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }
}