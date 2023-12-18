package com.example.beloved.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.MainActivity;
import com.example.beloved.R;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.models.Post;
import com.example.beloved.models.productItem;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {
    private Button continueBtn;
    private ImageView back;
    ArrayList<Post> productList;
    ProductAdapter adapter;
    ListView orderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.productListView);
        // Receive data from another screen using Intent
        List<OrderItem> orderItemList = getIntent().getParcelableArrayListExtra("orderItemList");

        if (orderItemList != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            OrderAdapter orderAdapter = new OrderAdapter(orderItemList);
            recyclerView.setAdapter(orderAdapter);
        }

        continueBtn = (Button) findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, OrderConfirmation.class);
                Checkout.this.startActivity(intent);
                Checkout.this.finish();
            }
        });
    }
}
