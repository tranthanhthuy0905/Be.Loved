package com.example.beloved.getStarted;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.beloved.MainActivity;
import com.example.beloved.R;
import com.google.android.material.snackbar.Snackbar;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        TextView loginQ = (TextView) findViewById(R.id.loginQ);
        loginQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SignUp.this, SignIn.class);
                SignUp.this.startActivity(intent);
                SignUp.this.finish();
            }
        });

        CheckBox checkTerm = (CheckBox) findViewById(R.id.checkTerm);
        checkTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Activity activity = SignUp.this;
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                }}}
                );

        Button signupBtn = (Button) findViewById(R.id.signup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Activity activity = SignUp.this;
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                }
                EditText username = findViewById(R.id.usernameInput);
                EditText phonenumber = findViewById(R.id.phoneInput);
                EditText password = findViewById(R.id.passwordInput);
//                Intent intent = new Intent(SignUp.this, ABC.class); // TODO: Changing ABC by Homescreen
//                intent.putExtra("Username", username.getText().toString());
//                intent.putExtra("phonenumber", phonenumber.getText().toString());
//                intent.putExtra("password", password.getText().toString());
            }}

        );
    }
}
