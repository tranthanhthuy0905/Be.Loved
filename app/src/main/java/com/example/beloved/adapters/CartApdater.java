package com.example.beloved.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.example.beloved.product.CreateItem;
import com.example.beloved.product.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartApdater extends RecyclerView.Adapter<CartApdater.viewHolder> {

    ArrayList<Post> prodList;
    Context context;

    public CartApdater(ArrayList<Post> prodList, Context context) {
        this.prodList = prodList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartApdater.viewHolder holder, int position) {
        final Post items = prodList.get(position);
        String imgUrl = items.getImage_url();
        if (imgUrl == null || imgUrl.isEmpty()) {
            holder.prod_image.setImageResource(R.drawable.placeholder);
        } else {
            try {
                Picasso.get().load(imgUrl).into(holder.prod_image);
            } catch (Exception e) {
                holder.prod_image.setImageResource(R.drawable.placeholder);
                Log.d("Cart Adapter", "cannot retrieve image for product");
            }
        }
        holder.title.setText(items.getTitle());
        holder.cost.setText(items.getPrice());

    }

    public void setDataList(ArrayList<Post> prodList){
        this.prodList = prodList;
    }
    @Override
    public int getItemCount() {
        return prodList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView prod_image;
        TextView title, cost, qty;
        Button proceedBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            prod_image = itemView.findViewById(R.id.cart_product_img);
            title = itemView.findViewById(R.id.product_name);
//            description = itemView.findViewById(R.id.product_description);
            cost = itemView.findViewById(R.id.product_cost);
            proceedBtn = itemView.findViewById(R.id.proceedBtn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            //to be specified later
                            Intent intent = new Intent(context, CreateItem.class);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

}
