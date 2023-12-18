package com.example.beloved;

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
import com.example.beloved.getStarted.SignUp;
import com.example.beloved.models.productItem;
import com.example.beloved.product.CreateItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// DO NOT USE THIS -- REFER TO LANDING PAGE INSTEAD
public class HomePage extends AppCompatActivity {
    ArrayList<productItem> productList;
    ProductAdapter adapter;
    RecyclerView prodLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        prodLayout = findViewById(R.id.productView);

        productList = new ArrayList<>();
        productList.add(new productItem(1, "Sample 1", "This is a 100% new dress.", R.drawable.elegant_young_woman_dress_with_crossed_hands_handbag_room,"9.99"));
        productList.add(new productItem(2, "Sample 2", "This is a 100% new dress.", R.drawable.elegant_woman_posing_classic_suit,"9.99"));
        productList.add(new productItem(3, "Sample 3", "This is a 100% new dress.", R.drawable.sample_top,"19.99"));
        productList.add(new productItem(1, "Sample 1", "This is a 100% new dress.", R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside,"9.99"));
        productList.add(new productItem(2, "Sample 2", "This is a 100% new dress.", R.drawable.sample_watch,"9.99"));
        productList.add(new productItem(3, "Sample 3", "This is a 100% new dress.", R.drawable.sample_top,"19.99"));
        productList.add(new productItem(1, "Sample 1", "This is a 100% new dress.", R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside,"9.99"));
        productList.add(new productItem(2, "Sample 2", "This is a 100% new dress.", R.drawable.sample_watch,"9.99"));
        productList.add(new productItem(3, "Sample 3", "This is a 100% new dress.", R.drawable.sample_top,"19.99"));

        adapter = new ProductAdapter(productList, this);
        prodLayout.setAdapter(adapter);
        prodLayout.setLayoutManager(new GridLayoutManager(this, 3));

        //add post creation Floating Action Button
        FloatingActionButton createBtn = (FloatingActionButton) findViewById(R.id.createPost_FAB);
        createBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, CreateItem.class);
                HomePage.this.startActivity(intent);
                HomePage.this.finish();
            }}
        ));

    }
}
