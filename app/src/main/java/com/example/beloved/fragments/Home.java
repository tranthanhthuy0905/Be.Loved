package com.example.beloved.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beloved.R;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.databinding.FragmentHomeBinding;
import com.example.beloved.product.Post;
import com.example.beloved.product.CreateItem;
import com.example.beloved.product.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Home extends Fragment {

    ArrayList<Post> productList;
    ProductAdapter adapter;
    RecyclerView prodLayout;
    FragmentHomeBinding binding;
    private static final String TAG = "Home";
    FirebaseDatabase db;
    DatabaseReference dataRef;
    DataSnapshot raw_data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String db_url = getResources().getString(R.string.db_url);
        db = FirebaseDatabase.getInstance(db_url);
        dataRef = db.getReference("products");
        productList = new ArrayList<>();
        //setup adapter
        adapter = new ProductAdapter(productList, getContext());
        productList.add(new Post("Sample", "a sample product - fake data", "https://thestylebungalow.com/wp-content/uploads/2019/05/IMG_9766.jpg", "9.99", "new"));
        prodLayout = binding.getRoot().findViewById(R.id.productView);
        prodLayout.setAdapter(adapter);
        prodLayout.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ArrayList<Post> tempList = new ArrayList<>();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()) {
                    HashMap<String, String> val = (HashMap<String, String>) child.getValue();
                    String title = val.get("title");
                    String status = val.get("status");
                    String created_at = val.get("created_at");
                    String description = val.get("description");
                    String price = val.get("price");
                    String image_url = val.get("image_url");
                    Post p = new Post(title, description, image_url, price, status);
                    p.setId(child.getKey());
                    p.setCreated_at(created_at);
                    productList.add(p);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FloatingActionButton createBtn = view.findViewById(R.id.createPost_FAB);
        createBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateItem.class);
                startActivity(intent);
            }}
        ));
    }
}

