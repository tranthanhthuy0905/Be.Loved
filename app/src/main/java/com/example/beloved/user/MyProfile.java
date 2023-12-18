package com.example.beloved.user;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.beloved.R;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.models.Post;
import com.example.beloved.models.UserProfile;
import com.example.beloved.product.CreateItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyProfile extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth auth;
    private DatabaseReference userRef;
    protected UserProfile profile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String db_url = getResources().getString(R.string.db_url);
        db = FirebaseDatabase.getInstance(db_url);
        userRef = db.getReference("user");

        ImageButton backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView emailView = findViewById(R.id.email);
        TextView addressView = findViewById(R.id.editTextAddress);
        TextView contactView = findViewById(R.id.editTextPhone);
        TextView usernameView = findViewById(R.id.username);
        String email = currentUser.getEmail() != null ? currentUser.getEmail() : "";
        String uid = currentUser.getUid() != null ? currentUser.getUid() : "";

        if (!email.isEmpty()) {
            // User is signed in
            emailView.setText(email);
            if (uid != null || uid.isEmpty()) {
                profile = new UserProfile(uid, email);
            }
        } else {
            emailView.setText("Log in to view details");
        }

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, get the email
//                    Log.d("PROFILE/GET-DATA", (String) dataSnapshot.getValue().toString());
                    String name = dataSnapshot.child("name").exists() ? dataSnapshot.child("name").getValue(String.class) : "";
                    String address = dataSnapshot.child("address").exists() ? dataSnapshot.child("address").getValue(String.class) : "";
                    String phone = dataSnapshot.child("phone").exists() ? dataSnapshot.child("phone").getValue(String.class) : "";
                    profile.setAddress(address);
                    profile.setContact(phone);
                    profile.setName(name);
                    Log.d("PROFILE",profile.getName());
                    usernameView.setText(name);
                    addressView.setText(address);
                    contactView.setText(phone);
                } else {
                    // User does not exist, insert a new record
                    UserProfile user = new UserProfile(uid, email); // Assuming you have a User class
                    userRef.child(uid).setValue(user);
                    profile = user;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("PROFILE/GET-DATA", "error getting data", error.toException());
            }
        });

        // Use the user information as needed
        usernameView.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            updatetoDB("name", String.valueOf(s));
        }
    });
        addressView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    updatetoDB("address", String.valueOf(s));
            }
        });
        contactView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updatetoDB("phone", String.valueOf(s));
            }
        });

}
    private void updatetoDB(String field, String value) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            String email = currentUser.getEmail();

            //find if user info exist
            userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User exists, update the email
                        userRef.child(uid).child(field).setValue(value);
                    } else {
                        // User does not exist, insert a new record
                        UserProfile user = new UserProfile(uid, email); // Assuming you have a User class
                        userRef.child(uid).setValue(user);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle error
                }
            });
        }
    }

}
