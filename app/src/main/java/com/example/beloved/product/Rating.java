package com.example.beloved.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beloved.LandingPage;
import com.example.beloved.MainActivity;
import com.example.beloved.R;
import com.example.beloved.getStarted.SignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rating extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText review;
    private Button submit;
    private ImageView back;
    private String ratingStr, reviewStr;

    DatabaseReference postDbRef;
    // TODO: Change this post_id by passing through Intent
    private String post_id = "-Nld6vfZcPUxyjwQI880";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String db_url = getResources().getString(R.string.db_url);
        postDbRef = FirebaseDatabase.getInstance(db_url).getReference("products");

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingStr = String.valueOf(rating);
                ratingBar.setRating(rating);
            }
        });

        review = (EditText) findViewById(R.id.addDescription);
        review.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Hide the keyboard
                    hideKeyboard(v);
                    return true; // consume the event
                }
                return false; // allow other listeners to handle the event
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewStr = review.getText().toString();

                DatabaseReference postRef = postDbRef.child(post_id);
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String ratingDb = dataSnapshot.child("rating").getValue(String.class);
                            String reviewDb= dataSnapshot.child("review").getValue(String.class);
                            if (ratingDb == null || !ratingDb.equals(ratingStr)) {
                                postRef.child("rating").setValue(ratingStr);
                                if (reviewDb == null || !reviewDb.equals(reviewStr)) {
                                    postRef.child("review").setValue(reviewStr);
                                    Toast.makeText(Rating.this, "Review successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Rating.this, LandingPage.class);
                                    Rating.this.startActivity(intent);
                                    Rating.this.finish();
                                }
                            }

                        } else {
                            Log.d("Rating", "Not change");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors here
                        Log.w("Rating", "Failed to read value.", databaseError.toException());
                    }
                });
            }
        });
    }
    private void hideKeyboard(View v) {
        // Hide the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}