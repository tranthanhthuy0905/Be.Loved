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
        // Receive data from another screen using Intent
        List<OrderItem> orderItemList = getIntent().getParcelableArrayListExtra("orderItemList");

        if (orderItemList != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            OrderAdapter orderAdapter = new OrderAdapter(orderItemList);
            recyclerView.setAdapter(orderAdapter);
        }
    }
}
