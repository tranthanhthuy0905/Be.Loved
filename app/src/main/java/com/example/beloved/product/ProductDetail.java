package com.example.beloved.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beloved.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    private ImageView back;

    private TextView descTV;
    private TextView created_atTV;
    private TextView titleTV;
    private TextView priceTV;
    private ImageView prod_imgIV;

    DatabaseReference postDbRef;
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String db_url = getResources().getString(R.string.db_url);
        postDbRef = FirebaseDatabase.getInstance(db_url).getReference("products");

        String storage_url = getResources().getString(R.string.storage_url);

        Intent intent = getIntent();
        String post_id = intent.getStringExtra("post_id");

        DatabaseReference post = postDbRef.child("-Nlc3RYkgYRG3pZEhELj");

        titleTV = (TextView) findViewById(R.id.prod_title);
        descTV = (TextView) findViewById(R.id.prod_desc);
        created_atTV = (TextView) findViewById(R.id.created_at);
        priceTV =  (TextView) findViewById(R.id.price_val);
        prod_imgIV = (ImageView) findViewById(R.id.prod_img);

        post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String title = intent.getStringExtra("prod_title");
                    String desc = intent.getStringExtra("prod_desc");
                    String price = intent.getStringExtra("prod_price");
                    String created_at = intent.getStringExtra("prod_created");
                    String img_url = intent.getStringExtra("prod_img");

                    titleTV.setText(title.substring(0, 1).toUpperCase() + title.substring(1));
                    descTV.setText(desc.substring(0, 1).toUpperCase() + desc.substring(1));
                    priceTV.setText(price);
                    created_atTV.setText(created_at);
                    Picasso.get().load(img_url).into(prod_imgIV);
//                    imageRef = FirebaseStorage.getInstance(storage_url).getReference("products").child("images/37488527-fd7a-496e-8c39-bda0f68cab3d");
//                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Picasso.get().load(uri.toString()).into(prod_imgIV);
//                            Toast.makeText(ProductDetail.this, "Successful" + uri, Toast.LENGTH_SHORT).show();
//                        }
//                    });

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
}


