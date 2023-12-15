package com.example.beloved.getStarted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beloved.LandingPage;
import com.example.beloved.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText email;
    private EditText password;

    private Button signInBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        TextView otherOptions = (TextView) findViewById(R.id.otherOptions);
        otherOptions.setText("------------Or sign in with------------");

        TextView question = (TextView) findViewById(R.id.question);
        question.setText("Don't have an account?");

        TextView qAction = (TextView) findViewById(R.id.qAction);
        qAction.setText("Sign Up");
        qAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SignIn.this, SignUp.class);
                SignIn.this.startActivity(intent);
                SignIn.this.finish();
            }
        });

        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);
        signInBtn = (Button) findViewById(R.id.navBtn);
        signInBtn.setText("Sign In");

        auth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Activity activity = SignIn.this;
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                }
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                loginUser(emailStr, passwordStr);
            }}

        );
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Toast.makeText(SignIn.this, "Log in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignIn.this, LandingPage.class);
                    SignIn.this.startActivity(intent);
                    SignIn.this.finish();
                }}
            )
            .addOnFailureListener(
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignIn.this, "Auth credential is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
    }
}
