package com.example.beloved.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beloved.R;
import com.example.beloved.databinding.FragmentHomeBinding;
import com.example.beloved.databinding.FragmentProfileBinding;
import com.example.beloved.models.UserProfile;
import com.example.beloved.product.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Profile extends Fragment {
    FragmentProfileBinding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference userRef;
    protected UserProfile profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String db_url = getResources().getString(R.string.db_url);
        db = FirebaseDatabase.getInstance(db_url);
        userRef = db.getReference("user");
        TextView emailView = view.findViewById(R.id.email);
        TextView usernameView = view.findViewById(R.id.username);
        TextView addressView = view.findViewById(R.id.editTextAddress);
        TextView contactView = view.findViewById(R.id.editTextPhone);

        if (currentUser != null) {
            // User is signed in
            String email = currentUser.getEmail() != null ? currentUser.getEmail() : "";
            String uid = currentUser.getUid() != null ? currentUser.getUid() : "";
            emailView.setText(email);
            if (email != null & uid != null) {
                profile = new UserProfile(uid, email);
            }
        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, get the email
//                    Log.d("PROFILE/GET-DATA", (String) dataSnapshot.getValue().toString());
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    String phone = dataSnapshot.child("phone").getValue(String.class);
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
        }

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