package com.example.beloved;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beloved.adapters.ChatRecyclerAdapter;
import com.example.beloved.models.ChatMessageModel;
import com.example.beloved.models.ChatroomModel;
import com.example.beloved.models.User;
import com.example.beloved.utils.AndroidUtil;
import com.example.beloved.utils.FirebaseUtil;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {
    User otherUser;
    String chatroomId;
    ChatroomModel chatroomModel;
    ChatRecyclerAdapter adapter;
    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),otherUser.getUid());

        messageInput = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUsername = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.chat_recycler_view);
        imageView = findViewById(R.id.profile_pic_image_view);

        backBtn.setOnClickListener((v)->{
            onBackPressed();
        });
        otherUsername.setText(otherUser.getName());

        sendMessageBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if(message.isEmpty())
                return;
            sendMessageToUser(message);
        }));

        getOrCreateChatroomModel();
        setupChatRecyclerView();
    }

    // WOrking example
    void setupChatRecyclerView(){
        Query query = FirebaseDatabase.getInstance("https://beloved-e77c6-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("chatrooms")
                .child(chatroomId)
                .child("chats")
                .orderByChild("timestamp");

        FirebaseRecyclerOptions<ChatMessageModel> options = new FirebaseRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class)
                .build();

        adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(positionStart);
            }
        });
    }

    void sendMessageToUser(String message){
        Timestamp now = Timestamp.now();
        chatroomModel.setLastMessageTimestamp(now);
        chatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        chatroomModel.setLastMessage(message);
        FirebaseUtil.getChatroomIdReference(chatroomId).child("lastMessage").setValue(chatroomModel.getLastMessage());
        FirebaseUtil.getChatroomIdReference(chatroomId).child("lastMessageSenderId").setValue(chatroomModel.getLastMessageSenderId());
        FirebaseUtil.getChatroomIdReference(chatroomId).child("lastMessageTimestamp").setValue(chatroomModel.getLastMessageTimestamp());

        ChatMessageModel chatMessageModel = new ChatMessageModel(message, FirebaseUtil.currentUserId(), Calendar.getInstance().getTime());

        DatabaseReference chatMessageRef = FirebaseUtil.getChatroomMessageReference(chatroomId);

        String chatId = chatMessageRef.push().getKey();
        chatMessageRef.child(chatId).setValue(chatMessageModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        messageInput.setText("");
                    }
                });
    }

    void getOrCreateChatroomModel(){
        FirebaseUtil.getChatroomReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Chatroom exists, retrieve the ChatroomModel
                    chatroomModel = dataSnapshot.getValue(ChatroomModel.class);
                } else {
                    // Chatroom doesn't exist, create a new one
                    createNewChatroom();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Log.e(TAG, "Error getting chatroom: " + databaseError.getMessage());
            }
        });
    }

    void createNewChatroom() {
        chatroomModel = new ChatroomModel(
                chatroomId,
                Arrays.asList(FirebaseUtil.currentUserId(), otherUser.getUid()),
                Timestamp.now(),
                ""
        );

        FirebaseUtil.getChatroomIdReference(chatroomId).setValue(chatroomModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Chatroom created successfully
                        Log.d(TAG, "Chatroom created successfully");
                    } else {
                        // Handle errors
                        Log.e(TAG, "Error creating chatroom: " + task.getException().getMessage());
                    }
                });
    }

}