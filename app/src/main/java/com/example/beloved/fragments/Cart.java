package com.example.beloved.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beloved.R;
import com.example.beloved.product.Checkout;
import com.example.beloved.product.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        Button checkoutButton = view.findViewById(R.id.checkoutBtn);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example for passing data to Checkout screen
                List<OrderItem> orderItemList = new ArrayList<>();
                orderItemList.add(new OrderItem(R.drawable.sample_top, "Product 1", "$10.99"));
                orderItemList.add(new OrderItem(R.drawable.elegant_young_woman_dress_with_crossed_hands_handbag_room, "Product 2", "$20.49"));
                orderItemList.add(new OrderItem(R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside, "Product 3", "$15.99"));
                orderItemList.add(new OrderItem(R.drawable.elegant_young_woman_dress_with_crossed_hands_handbag_room, "Product 4", "$15.99"));
                orderItemList.add(new OrderItem(R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside, "Product 5", "$15.99"));
                orderItemList.add(new OrderItem(R.drawable.elegant_woman_posing_classic_suit, "Product 6", "$15.99"));
                orderItemList.add(new OrderItem(R.drawable.sample_watch, "Product 7", "$15.99"));

                // Create an Intent to start the Checkout activity
                Intent intent = new Intent(getActivity(), Checkout.class);

                // Pass the list of OrderItem objects to the Checkout activity
                intent.putParcelableArrayListExtra("orderItemList", new ArrayList<>(orderItemList));

                // Start the Checkout activity
                startActivity(intent);
            }
        });

        return view;
    }
}