package com.example.beloved.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;

import com.example.beloved.R;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.databinding.FragmentHomeBinding;
import com.example.beloved.models.Post;
import com.example.beloved.product.CreateItem;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String db_url = getResources().getString(R.string.db_url);
        db = FirebaseDatabase.getInstance(db_url);
        dataRef = db.getReference("products");
        productList = new ArrayList<>();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Post> prod = new ArrayList();
                for (DataSnapshot data: snapshot.getChildren()) {
//                    String title = data.child("title").getValue(String.class);
//                    String status = data.child("status").getValue(String.class);
//                    String created_at = data.child("created_at").getValue(String.class);
//                    String description = data.child("description").getValue(String.class);
//                    String price = data.child("price").getValue(String.class);
//                    String image_url = data.child("image_url").getValue(String.class);
//                    String seller_id = data.child("seller_id").getValue(String.class);
//                    Post p = new Post(title, description, image_url, price, status);
//                    p.setId(data.getKey());
                    Post p = data.getValue(Post.class);
                    prod.add(p);
                }
                productList.addAll(prod);
                Log.d(TAG, productList.toString());
                adapter.setDataList(productList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        setup adapter
        Post sampleProd = new Post("Sample", "a sample product - fake data", "https://thestylebungalow.com/wp-content/uploads/2019/05/IMG_9766.jpg", "9.99", "new");
        productList.add(sampleProd);
//        getData();

        Log.d(TAG, productList.toString());

        adapter = new ProductAdapter(productList, getContext());
        prodLayout = binding.getRoot().findViewById(R.id.productView);
        prodLayout.setAdapter(adapter);
        prodLayout.setLayoutManager(new GridLayoutManager(getContext(), 2));

        FloatingActionButton createBtn = view.findViewById(R.id.createPost_FAB);
        createBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateItem.class);
                startActivity(intent);
            }}
        ));


    }
    public void getData() {


    }
}

