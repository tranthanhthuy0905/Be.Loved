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

import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.fragments.Home;
import com.example.beloved.getStarted.SignUp;
import com.example.beloved.models.productItem;
import com.example.beloved.product.CreateItem;
import com.example.beloved.models.Post;
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
        prodLayout = findViewById(R.id.productView);
        String db_url = getResources().getString(R.string.db_url);
        db = FirebaseDatabase.getInstance(db_url);
        dataRef = db.getReference("products");
        productList = new ArrayList<>();
        //setup adapter
        adapter = new ProductAdapter(productList, this);
        productList.add(new Post("Sample", "a sample product - fake data", "https://thestylebungalow.com/wp-content/uploads/2019/05/IMG_9766.jpg", "9.99", "new"));
        prodLayout = findViewById(R.id.productView);
        prodLayout.setAdapter(adapter);
        prodLayout.setLayoutManager(new GridLayoutManager(this, 3));
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

        FloatingActionButton createBtn = findViewById(R.id.createPost_FAB);
        createBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, CreateItem.class);
                startActivity(intent);
            }}
        ));
    }
}
