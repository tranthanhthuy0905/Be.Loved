package com.example.beloved.fragments;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beloved.LandingPage;
import com.example.beloved.MyProfile;
import com.example.beloved.R;
import com.example.beloved.databinding.FragmentHomeBinding;
import com.example.beloved.databinding.FragmentProfileBinding;
import com.example.beloved.getStarted.SignIn;
import com.example.beloved.models.UserProfile;
import com.example.beloved.models.Post;
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
        TextView emailView = view.findViewById(R.id.username);

        Button logout = view.findViewById(R.id.logout);
        logout.setVisibility(currentUser != null? View.GONE : View.VISIBLE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    FirebaseAuth.getInstance().signOut();
                    Intent logout = new Intent(getActivity(), LandingPage.class);
                    startActivity(logout);
                } else {
                    Toast.makeText(getActivity(), "You are not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (currentUser != null) {
            // User is signed in
            String email = currentUser.getEmail() != null ? currentUser.getEmail() : "";
            String uid = currentUser.getUid() != null ? currentUser.getUid() : "";
            emailView.setText(email);
            if (email != null & uid != null) {
                profile = new UserProfile(uid, email);
            }
        } else {
            emailView.setText("Log in to view details");
            emailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SignIn.class));
                }
            });
        }

        ImageButton viewProfile = view.findViewById(R.id.view_profileBtn);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Intent myProfile = new Intent(getActivity(), MyProfile.class);
                    startActivity(myProfile);
                } else {
                    Toast.makeText(getActivity(), "You are not signed in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}