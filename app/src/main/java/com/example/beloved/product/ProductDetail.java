package com.example.beloved.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beloved.LandingPage;
import com.example.beloved.R;
import com.example.beloved.fragments.Cart;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProductDetail extends AppCompatActivity {

    private ImageView back;

    private TextView descTV;
    private TextView created_atTV;
    private TextView titleTV;
    private TextView priceTV;
    private ImageView prod_imgIV;

    DatabaseReference postDbRef;
    FirebaseAuth auth;
    private Button addCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        String db_url = getResources().getString(R.string.db_url);
        postDbRef = FirebaseDatabase.getInstance(db_url).getReference("products");

        titleTV = (TextView) findViewById(R.id.prod_title);
        descTV = (TextView) findViewById(R.id.prod_desc);
        created_atTV = (TextView) findViewById(R.id.created_at);
        priceTV =  (TextView) findViewById(R.id.price_val);
        prod_imgIV = (ImageView) findViewById(R.id.prod_img);

        Intent intent = getIntent();
        String post_id =  intent.getStringExtra("post_id");
        Log.d("PROD_DETAIL", "item key" + post_id);
        if (post_id == null) {
            this.finishActivity(0);
        } else {
            DatabaseReference post = postDbRef.child(post_id);
            post.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String title = dataSnapshot.child("title").getValue(String.class);
                        String desc = dataSnapshot.child("description").getValue(String.class);
                        String price = dataSnapshot.child("price").getValue(String.class);
                        String created_at = dataSnapshot.child("created_at").getValue(String.class);
                        String img_url = dataSnapshot.child("image_url").getValue(String.class);

                        titleTV.setText(title.substring(0, 1).toUpperCase() + title.substring(1));
                        descTV.setText(desc.substring(0, 1).toUpperCase() + desc.substring(1));
                        priceTV.setText(price);
                        created_atTV.setText(created_at);
                        try {
                            Picasso.get().load(img_url).into(prod_imgIV);
                        } catch (Exception e) {
                            prod_imgIV.setImageResource(R.drawable.placeholder);
                            Log.d("PRODUCT DETAIL","cannot retrieve image for product");
                        }

                    } else {
                        // Handle the case where the user with the specified ID doesn't exist
                        Log.d("FirebaseData", "Post not found with ID: " + post_id);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


        addCart = (Button) findViewById(R.id.addCart);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();
                String uid = currentUser.getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance(db_url).getReference("user");
                DatabaseReference userEntry = uid != null ? userRef.child(uid) : null;

                if (userEntry != null && userRef.child(uid) != null) {
                    DatabaseReference userCart = userRef.child(uid).child("cart");
                    userCart.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> currentList = new ArrayList<>();
                            if (snapshot.exists()) {
                                List<String> dataList = (List<String>) snapshot.getValue();
                                currentList.addAll(dataList);
                            }
                            currentList.add(post_id);
                            userCart.setValue(currentList);
                            Toast.makeText(ProductDetail.this, "Add product to cart successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProductDetail.this, LandingPage.class);
                            ProductDetail.this.startActivity(intent);
                            ProductDetail.this.finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("CART", "User stops adding product to cart", error.toException());
                        }
                    });
                } else {
                    Log.d("CART", "Fail to add product to user cart");
                }
            }
        });
    }
}


