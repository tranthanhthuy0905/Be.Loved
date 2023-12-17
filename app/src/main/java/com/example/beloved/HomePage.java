package com.example.beloved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.beloved.adapters.PostAdapter;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.fragments.Home;
import com.example.beloved.getStarted.SignUp;
import com.example.beloved.models.productItem;
import com.example.beloved.product.CreateItem;
import com.example.beloved.models.Post;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

// DO NOT USE THIS -- REFER TO LANDING PAGE INSTEAD
public class HomePage extends AppCompatActivity {
    ArrayList<Post> productList;
    PostAdapter postAdapter;

    ProductAdapter adapter;
    RecyclerView prodLayout;
    private static final String TAG = "Home";
    FirebaseDatabase db;
    DatabaseReference dataRef;
    DataSnapshot raw_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        String db_url = getResources().getString(R.string.db_url);
        db = FirebaseDatabase.getInstance(db_url);
        dataRef = db.getReference("products");
        productList = new ArrayList<>();
        prodLayout = findViewById(R.id.productView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        prodLayout.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Post> options
                = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(db.getReference("products"), Post.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        postAdapter = new PostAdapter(options);
        prodLayout.setAdapter(postAdapter);

        FloatingActionButton createBtn = findViewById(R.id.createPost_FAB);
        postAdapter.startListening();
    }

}
