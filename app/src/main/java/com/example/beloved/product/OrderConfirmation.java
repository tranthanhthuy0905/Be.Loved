package com.example.beloved.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.beloved.R;

public class OrderConfirmation extends AppCompatActivity {

    private TextView rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done_order);

        rate = (TextView) findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmation.this, Rating.class);
                OrderConfirmation.this.startActivity(intent);
                OrderConfirmation.this.finish();
            }
        });
    }
}
