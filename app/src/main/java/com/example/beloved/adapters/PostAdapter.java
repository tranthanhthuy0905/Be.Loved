package com.example.beloved.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.R;
import com.example.beloved.models.Post;
import com.example.beloved.product.CreateItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

public class PostAdapter extends FirebaseRecyclerAdapter<
        Post, PostAdapter.PostsViewholder> {

    public PostAdapter(
            @NonNull FirebaseRecyclerOptions<Post> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "Post.xml") iwth data in
    // model class(here "Post.class")
    @Override
    protected void
    onBindViewHolder(@NonNull PostsViewholder holder,
                     int position, @NonNull Post model)
    {

        String imgUrl = model.getImage_url();
        if (imgUrl == null || imgUrl.isEmpty()) {
            holder.prod_image.setImageResource(R.drawable.placeholder);
        } else {
            try {
                Picasso.get().load(imgUrl).into(holder.prod_image);
            } catch (Exception e) {
                holder.prod_image.setImageResource(R.drawable.placeholder);
                Log.d("Post Adapter", "cannot retrieve image for product");
            }
        }
        holder.title.setText(model.getTitle());
        holder.cost.setText(model.getPrice());
        Log.d("Post Adapter", model.getTitle());

    }

    // Function to tell the class about the Card view (here
    // "Post.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public PostsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);
        return new PostsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "Post.xml")
    class PostsViewholder extends RecyclerView.ViewHolder {
        ImageView prod_image;
        TextView title, cost;
        public PostsViewholder(@NonNull View itemView){
            super(itemView);
            prod_image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_name);
//            description = itemView.findViewById(R.id.product_description);
            cost = itemView.findViewById(R.id.product_cost);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (parent.getContext() != null) {
//                        int pos = getBindingAdapterPosition();
//                        if (pos != RecyclerView.NO_POSITION) {
//                            //to be specified later
//                            Intent intent = new Intent(parent.getContext(), CreateItem.class);
//                            parent.getContext().startActivity(intent);
//                        }
//                    }
//                }
//            });
        }

    }
}