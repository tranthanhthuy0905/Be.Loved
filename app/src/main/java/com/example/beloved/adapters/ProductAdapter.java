package com.example.beloved.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.example.beloved.fragments.Cart;
import com.example.beloved.fragments.Chat;
import com.example.beloved.models.productItem;
import com.example.beloved.product.CreateItem;
import com.example.beloved.models.Post;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
// DO NOT USE - USE POST ADAPTER instead
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder> {

    ArrayList<Post> prodList;
    Context context;

    public ProductAdapter(ArrayList<Post> prodList, Context context) {
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
        final Post items = prodList.get(position);
        String imgUrl = items.getImage_url();
        if (imgUrl == null || imgUrl.isEmpty()) {
            holder.prod_image.setImageResource(R.drawable.placeholder);
        } else {
            try {
                Picasso.get().load(imgUrl).into(holder.prod_image);
            } catch (Exception e) {
                holder.prod_image.setImageResource(R.drawable.placeholder);
                Log.d("Product Adapter", "cannot retrieve image for product");
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
        TextView title, cost;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            prod_image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_name);
//            description = itemView.findViewById(R.id.product_description);
            cost = itemView.findViewById(R.id.product_cost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context != null) {
                        int pos = getBindingAdapterPosition();
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
