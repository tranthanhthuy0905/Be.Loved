package com.example.beloved.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beloved.HomePage;
import com.example.beloved.R;
import com.example.beloved.adapters.ProductAdapter;
import com.example.beloved.models.productItem;
import com.example.beloved.product.CreateItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Home extends Fragment {

    ArrayList<productItem> productList;
    ProductAdapter adapter;
    RecyclerView prodLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        prodLayout = getView().findViewById (R.id.productView);

//        productList = new ArrayList<>();
//        productList.add(new productItem(1, "Sample 1", "This is a 100% new dress.", R.drawable.elegant_young_woman_dress_with_crossed_hands_handbag_room,"9.99"));
//        productList.add(new productItem(2, "Sample 2", "This is a 100% new dress.", R.drawable.elegant_woman_posing_classic_suit,"9.99"));
//        productList.add(new productItem(3, "Sample 3", "This is a 100% new dress.", R.drawable.sample_top,"19.99"));
//        productList.add(new productItem(1, "Sample 1", "This is a 100% new dress.", R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside,"9.99"));
//        productList.add(new productItem(2, "Sample 2", "This is a 100% new dress.", R.drawable.sample_watch,"9.99"));
//        productList.add(new productItem(3, "Sample 3", "This is a 100% new dress.", R.drawable.sample_top,"19.99"));
//        productList.add(new productItem(1, "Sample 1", "This is a 100% new dress.", R.drawable.beautiful_lady_trench_coat_black_shoes_heels_standing_dreamily_looking_aside,"9.99"));
//        productList.add(new productItem(2, "Sample 2", "This is a 100% new dress.", R.drawable.sample_watch,"9.99"));
//        productList.add(new productItem(3, "Sample 3", "This is a 100% new dress.", R.drawable.sample_top,"19.99"));
//
//        adapter = new ProductAdapter(productList, getContext());
//        prodLayout.setAdapter(adapter);
//        prodLayout.setLayoutManager(new GridLayoutManager(getContext(), 3));
//
//        //add post creation Floating Action Button
//        FloatingActionButton createBtn = getView().findViewById(R.id.createPost_FAB);
//        createBtn.setOnClickListener((v -> {
//            Intent intent = new Intent(getContext(), CreateItem.class);
//            Home.this.startActivity(intent);
//        }
//        ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        // Assign product list to ItemAdapter
        adapter = new ProductAdapter(productList, getContext());
        // Set the LayoutManager that
        // this RecyclerView will use.
        prodLayout = view.findViewById(R.id.productView);
        prodLayout.setAdapter(adapter);
        prodLayout.setLayoutManager(new GridLayoutManager(getContext(), 3));
        FloatingActionButton createBtn = view.findViewById(R.id.createPost_FAB);
        createBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateItem.class);
                startActivity(intent);
            }}
        ));

        // adapter instance is set to the
        // recyclerview to inflate the items.
    }
}