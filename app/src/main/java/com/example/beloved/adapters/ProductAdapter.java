package com.example.beloved.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.example.beloved.models.productItem;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {

    ArrayList<productItem> prodList;
    Context context;

    public ProductAdapter(ArrayList<productItem> prodList, Context context) {
        this.prodList = prodList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.viewHolder holder, int position) {
        final productItem items = prodList.get(position);
        holder.prod_image.setImageResource(items.getImageUrl());
//        holder.description.setText(items.getDescription());
        holder.title.setText(items.getTitle());
        holder.cost.setText(items.getCost());
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView prod_image;
        TextView title, cost;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            prod_image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_name);
//            description = itemView.findViewById(R.id.product_description);
            cost = itemView.findViewById(R.id.product_cost);
        }
    }

}
