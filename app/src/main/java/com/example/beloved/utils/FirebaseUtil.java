package com.example.beloved.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtil {

    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static boolean isLoggedIn(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }

//    public static DocumentReference currentUserDetails(){
//        return FirebaseDatabase.getInstance().getReference("users").get(currentUserId());
//    }

    public static DatabaseReference allUserCollectionReference(){
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public static DatabaseReference getChatroomReference(){
        return FirebaseDatabase.getInstance("https://beloved-e77c6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("chatrooms");
    }

    public static DatabaseReference getChatroomIdReference(String chatroomId){
        return FirebaseDatabase.getInstance("https://beloved-e77c6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("chatrooms").child(chatroomId);
    }

    public static DatabaseReference getChatroomMessageReference(String chatroomId){
        return getChatroomIdReference(chatroomId).child("chats");
    }

    public static String getChatroomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }

    public static DatabaseReference allChatroomCollectionReference(){
        return getChatroomReference();
    }

    public static DatabaseReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(FirebaseUtil.currentUserId())){
            return allUserCollectionReference().child(userIds.get(1));
        }else{
            return allUserCollectionReference().child(userIds.get(0));
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public static StorageReference  getCurrentProfilePicStorageRef(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.currentUserId());
    }

    public static StorageReference  getOtherProfilePicStorageRef(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId);
    }
}










