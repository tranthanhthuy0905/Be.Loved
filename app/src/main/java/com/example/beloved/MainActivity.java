package com.example.beloved;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.beloved.getStarted.SignIn;
import com.example.beloved.getStarted.SignUp;
import com.example.beloved.product.CreateItem;
import com.example.beloved.product.OrderConfirmation;
import com.example.beloved.product.ProductDetail;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getting_started);

        Button getStartedBtn = (Button) findViewById(R.id.getStarted);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }}
        );

        TextView loginQ = (TextView) findViewById(R.id.qAction);
        loginQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }
}