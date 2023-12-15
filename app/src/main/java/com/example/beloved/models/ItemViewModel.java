package com.example.beloved.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beloved.product.Post;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<Post> selectedItem = new MutableLiveData<Post>();
    public void selectPost(Post post) {
        selectedItem.setValue(post);
    }
    public LiveData<Post> getSelectedItem() {
        return selectedItem;
    }
}
