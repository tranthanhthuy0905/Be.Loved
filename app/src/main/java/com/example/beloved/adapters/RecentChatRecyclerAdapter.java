package com.example.beloved.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.ChatActivity;
import com.example.beloved.R;
import com.example.beloved.models.ChatroomModel;
import com.example.beloved.models.User;
import com.example.beloved.product.OrderAdapter;
import com.example.beloved.product.OrderItem;
import com.example.beloved.utils.AndroidUtil;
import com.example.beloved.utils.FirebaseUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class RecentChatRecyclerAdapter extends RecyclerView.Adapter<RecentChatRecyclerAdapter.ChatroomModelViewHolder> {

    private final List<User> userList;
    private final Context context;  // Add this member variable

    public RecentChatRecyclerAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    @Override
    public void onBindViewHolder(@NonNull RecentChatRecyclerAdapter.ChatroomModelViewHolder holder, int position) {
        User user = userList.get(position);

        // Set data to views
        holder.usernameText.setText(user.getUsername());
        holder.itemView.setOnClickListener(v -> {
            //navigate to chat activity
            Intent intent = new Intent(context, ChatActivity.class);
            AndroidUtil.passUserModelAsIntent(intent, user);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row,parent,false);
        return new ChatroomModelViewHolder(view);
    }

    class ChatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView lastMessageText;
        TextView lastMessageTime;
        ImageView profilePic;

        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
