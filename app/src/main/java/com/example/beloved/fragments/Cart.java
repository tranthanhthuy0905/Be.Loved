package com.example.beloved.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.beloved.R;
import com.example.beloved.adapters.CartsAdapter;
import com.example.beloved.databinding.FragmentCartBinding;
import com.example.beloved.models.Post;
import com.example.beloved.product.Checkout;
import com.example.beloved.product.OrderItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {


    private static final String TAG = "Home";
    FirebaseDatabase db;
    DatabaseReference dataRef;
    FragmentCartBinding binding;
    ArrayList<Post> cartItems;
    RecyclerView cartLayout;
    CartsAdapter adapter;
    List<Post> arr;
    FirebaseAuth auth;
    ArrayList<String> idsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }
        @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        String db_url = getResources().getString(R.string.db_url);
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance(db_url);
        DatabaseReference userRef = db.getReference("user");
        DatabaseReference productRef = db.getReference("products");
        String email = currentUser.getEmail();
        String uid = currentUser.getUid();
        idsList = new ArrayList<>();
//        idsList.add("-Nld-gNjEpqYc2-UXSPaddn");
        cartItems = new ArrayList<>();
        ArrayList<OrderItem> orderItemList = new ArrayList<>();

        DatabaseReference userEntry = uid != null ? userRef.child(uid) : null;
        if (userEntry != null && userEntry != null) {
            DatabaseReference userCart = userEntry.child("cart");
            userCart.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        idsList.add(item.getValue(String.class));
                        Log.d("CART", "ids List:" + idsList.toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FirebaseData", "User Cart get error", error.toException());
                }
            });

            Log.d("CART", "ids List outside:" + idsList.toString());

            cartLayout = view.findViewById(R.id.cartView);

            productRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartItems = new ArrayList<>();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        if (idsList.contains(item.getKey())) {
                            Post p = item.getValue(Post.class);
                            p.setKey(item.getKey());
                            Log.d("CART product getter:", item.getKey() + "" + p.getTitle());
                            cartItems.add(p);
                            adapter.addItem(p);
                            adapter.notifyDataSetChanged();
                            orderItemList.add(new OrderItem(p.getImage_url(),p.getTitle(),p.getPrice()));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FirebaseData", "User Cart get error", error.toException());
                }
            });

            adapter = new CartsAdapter(cartItems, getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setReverseLayout(true);
            cartLayout.setLayoutManager(layoutManager);
            cartLayout.setAdapter(adapter);

            Button checkoutBtn = view.findViewById(R.id.checkout);

            checkoutBtn.setOnClickListener(v -> {
                // Example for passing data to Checkout screen
                //add adapter
                // Create an Intent to start the Checkout activity
                Intent intent = new Intent(getActivity(), Checkout.class);
                // Pass the list of OrderItem objects to the Checkout activity
                intent.putParcelableArrayListExtra("orderItemList", orderItemList);
                // Start the Checkout activity
                startActivity(intent);
            });
        } else {
            TextView alert = new TextView(view.getContext());
            alert.setText("Sign in to add items");
        }
    }
    @Override
        public void onStart(){
            super.onStart();
            Log.d("CART", "id list on view created:" + idsList.toString());
        }
}
