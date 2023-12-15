package com.example.beloved.getStarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beloved.LandingPage;
import com.example.beloved.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText phonenumber;
    private EditText password;

    private Button signUpBtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        TextView qAction = (TextView) findViewById(R.id.qAction);

        qAction.setOnClickListener(new View.OnClickListener() {
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

        username = (EditText) findViewById(R.id.usernameInput);
        email = (EditText) findViewById(R.id.emailInput);
        phonenumber = (EditText) findViewById(R.id.phoneInput);
        password = (EditText) findViewById(R.id.passwordInput);
        signUpBtn = (Button) findViewById(R.id.navBtn);
        signUpBtn.setText("Sign Up");

        auth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Activity activity = SignUp.this;
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                }
                String usernameStr = username.getText().toString();
                String emailStr = email.getText().toString();
                String phonenumberStr = phonenumber.getText().toString();
                String passwordStr = password.getText().toString();
                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(usernameStr) || TextUtils.isEmpty(phonenumberStr) || TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(SignUp.this, "Empty credentials", Toast.LENGTH_SHORT).show();
                } else if (passwordStr.length() < 8) {
                    Toast.makeText(SignUp.this, "Password length must be more than 8", Toast.LENGTH_SHORT).show();
                } else {
                    Integer phoneNumber = Integer.parseInt(phonenumber.getText().toString());
                    registerUser(usernameStr, emailStr, phoneNumber, passwordStr);
                }
            }}

        );
    }

    private void registerUser(String username, String email, Integer phoneNumber, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    // TODO: Add Homescreen here
                    Intent intent = new Intent(SignUp.this, LandingPage.class);
                    SignUp.this.startActivity(intent);
                    SignUp.this.finish();
                } else {
                    Toast.makeText(SignUp.this,"Fail to sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    };
}
