package com.example.beloved.adapters;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.example.beloved.models.Post;
import com.example.beloved.product.Checkout;
import com.example.beloved.product.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
// DO NOT USE - USE POST ADAPTER instead
public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.viewHolder> {

    ArrayList<Post> prodList;
    Context context;

    public CartsAdapter(ArrayList<Post> prodList, Context context) {
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
    public void onBindViewHolder(@NonNull CartsAdapter.viewHolder holder, int position) {
        final Post items = prodList.get(position);
        String imgUrl = items.getImage_url();
        if (imgUrl == null || imgUrl.isEmpty()) {
            holder.prod_image.setImageResource(R.drawable.placeholder);
        } else {
            try {
                Picasso.get().load(imgUrl).into(holder.prod_image);
            } catch (Exception e) {
                holder.prod_image.setImageResource(R.drawable.placeholder);
                Log.d("Cart Adapter", "cannot retrieve image for Cart");
            }
        }
        holder.title.setText(items.getTitle());
        holder.cost.setText(items.getPrice());

        holder.proceedBtn.setOnClickListener(v -> {
            // Example for passing data to Checkout screen
            //add adapter
            // Create an Intent to start the Checkout activity
            Intent intent = new Intent(holder.itemView.getContext(), Checkout.class);
            ArrayList<OrderItem> orderItemList = new ArrayList<>();

            orderItemList.add(new OrderItem(items.getImage_url(), items.getTitle(), items.getPrice()));
            // Pass the list of OrderItem objects to the Checkout activity
            intent.putParcelableArrayListExtra("orderItemList", orderItemList);
            // Start the Checkout activity
            holder.itemView.getContext().startActivity(intent);
        });


    }

    public void setDataList(ArrayList<Post> prodList){
        this.prodList = prodList;
    }

    @Override
    public int getItemCount() {
        return prodList.isEmpty() ? 0 : prodList.size();
    }
    public void addItem(Post newP) {
        this.prodList.add(newP);
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView prod_image;
        TextView title, cost;
//        ImageButton deleteBtn;
        Button proceedBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            prod_image = itemView.findViewById(R.id.cart_product_img);
            title = itemView.findViewById(R.id.product_name);
            cost = itemView.findViewById(R.id.product_cost);
//            deleteBtn = itemView.findViewById(R.id.delete_from_cdeleart);
            proceedBtn = itemView.findViewById(R.id.proceedBtn);
        }
    }

}
