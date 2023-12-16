package com.example.beloved.product;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.models.productItem;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {
    ArrayList<productItem> productList;
    ProductAdapter adapter;
    ListView orderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        RecyclerView recyclerView = findViewById(R.id.productListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // You can adjust the number of columns


        // Sample data for testing
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem(R.drawable.upload, "Product 1", "$10.99"));
        orderItemList.add(new OrderItem(R.drawable.chat_icon, "Product 2", "$20.49"));
        orderItemList.add(new OrderItem(R.drawable.decrease, "Product 3", "$15.99"));
        orderItemList.add(new OrderItem(R.drawable.elegant_young_woman_dress_with_crossed_hands_handbag_room, "Product 3", "$15.99"));
        orderItemList.add(new OrderItem(R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside, "Product 3", "$15.99"));
        orderItemList.add(new OrderItem(R.drawable.elegant_woman_posing_classic_suit, "Product 3", "$15.99"));
        orderItemList.add(new OrderItem(R.drawable.sample_watch, "Product 3", "$15.99"));


        OrderAdapter orderAdapter = new OrderAdapter(orderItemList);
        recyclerView.setAdapter(orderAdapter);
    }
}
