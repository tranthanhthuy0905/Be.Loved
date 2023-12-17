package com.example.beloved.fragments;

import static android.widget.GridLayout.HORIZONTAL;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

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
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.example.beloved.MainActivity;
import com.example.beloved.R;
import com.example.beloved.adapters.PostAdapter;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.databinding.FragmentHomeBinding;
import com.example.beloved.models.Post;
import com.example.beloved.product.CreateItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
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
    PostAdapter postAdapter;
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
        prodLayout = binding.getRoot().findViewById(R.id.productView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        prodLayout.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(productList, getActivity());
        prodLayout.setAdapter(adapter);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList = new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Post p = item.getValue(Post.class);
                    p.setKey(item.getKey());
                    Log.d("CART product getter:", item.getKey() + "" + p.getTitle());
                    productList.add(p);
                    adapter.addPost(p);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Firebase Data Listener", error.toException());
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

