package com.example.beloved.getStarted;

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

import com.example.beloved.R;

public class SignIn extends AppCompatActivity {

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

        Button signInBtn = (Button) findViewById(R.id.navBtn);
        signInBtn.setText("Sign In");

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Activity activity = SignIn.this;
                View focusedView = activity.getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                }
                EditText username = findViewById(R.id.usernameInput);
                EditText password = findViewById(R.id.passwordInput);
//                Intent intent = new Intent(SignIn.this, ABC.class);  // TODO: Changing ABC by Homescreen
//                intent.putExtra("Username", username.getText().toString());
//                intent.putExtra("password", password.getText().toString());
            }}

        );
    }
}
