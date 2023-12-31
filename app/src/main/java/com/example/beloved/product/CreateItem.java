package com.example.beloved.product;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beloved.LandingPage;
import com.example.beloved.R;
import com.example.beloved.fragments.Home;
import com.example.beloved.models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CreateItem extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView uploadImage;
    private ImageView productImg;
    private Button createBtn;
    private EditText title;
    private EditText description;
    private EditText price;
    private Button closeBtn;

    private Uri selectedImgUri;
    private String selectedImgStr;
    DatabaseReference postDbRef;
    StorageReference storageRef;
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_item);

        closeBtn = (Button) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uploadImage = (ImageView) findViewById(R.id.uploadImgIcon);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        createBtn = (Button) findViewById(R.id.createBtn);
        title = (EditText) findViewById(R.id.addTitle);
        description = (EditText) findViewById(R.id.addDescription);
        price = (EditText) findViewById(R.id.priceInput);

        String db_url = getResources().getString(R.string.db_url);
        postDbRef = FirebaseDatabase.getInstance(db_url).getReference("products");
        String storage_url = getResources().getString(R.string.storage_url);
        storageRef = FirebaseStorage.getInstance(storage_url).getReference("products");
        imageRef = storageRef.child("images/" + UUID.randomUUID().toString());
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPostData();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            productImg = (ImageView) findViewById(R.id.productImg);
            selectedImgUri = data.getData();
            UploadTask task = imageRef.putFile(selectedImgUri);
            task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            selectedImgStr = uri.toString();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Failed image upload", "Post");
                }
            });
            productImg.setImageURI(selectedImgUri);
            productImg.setVisibility(View.VISIBLE);
        }
    }
    private void hideKeyboard(View v) {
        // Hide the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    private void insertPostData() {
        String titleStr = title.getText().toString();
        String descriptionStr = description.getText().toString();
        String priceInt = price.getText().toString();

        Post newPost = new Post(titleStr, descriptionStr, selectedImgStr, priceInt, "new");
        String post_id = postDbRef.push().getKey();
        postDbRef.child(post_id).setValue(newPost)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateItem.this, "Create post successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateItem.this, LandingPage.class);
                        CreateItem.this.startActivity(intent);
                        CreateItem.this.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateItem.this, "Fail to create post", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
