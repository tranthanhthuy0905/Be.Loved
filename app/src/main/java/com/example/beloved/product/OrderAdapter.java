package com.example.beloved.product;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.squareup.picasso.Picasso;

import java.util.List;

// OrderAdapter.java
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<OrderItem> orderItemList;

    public OrderAdapter(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);
        String imgUrl = orderItem.getImageUrl();
        // Set data to views
        holder.productName.setText(orderItem.getProductName());
        holder.productPrice.setText(orderItem.getProductPrice());
        if (imgUrl == null || imgUrl.isEmpty()) {
            holder.productImage.setImageResource(R.drawable.placeholder);
        } else {
            try {
                Picasso.get().load(imgUrl).into(holder.productImage);
            } catch (Exception e) {
                holder.productImage.setImageResource(R.drawable.placeholder);
                Log.d("Cart Adapter", "cannot retrieve image for Cart");
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}

