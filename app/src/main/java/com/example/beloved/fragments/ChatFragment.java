package com.example.beloved.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beloved.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.beloved.adapters.RecentChatRecyclerAdapter;
import com.example.beloved.models.ChatroomModel;
import com.example.beloved.models.User;
import com.example.beloved.utils.FirebaseUtil;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    RecentChatRecyclerAdapter adapter;


    public ChatFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyler_view);
        setupRecyclerView();

        return view;
    }

    void setupRecyclerView(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://beloved-e77c6-default-rtdb.asia-southeast1.firebasedatabase.app");

        FirebaseRecyclerOptions<User> options
                = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(db.getReference("users"),
                        new SnapshotParser<User >() {
                            @NonNull
                            @Override
                            public User parseSnapshot(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                // so i wanted to add the key of the node as a field in the node object.
                                return user;}}).build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new RecentChatRecyclerAdapter(getContext(), options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
