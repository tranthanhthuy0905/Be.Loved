package com.example.beloved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.EditText;

import com.example.beloved.fragments.Cart;
import com.example.beloved.fragments.ChatFragment;
import com.example.beloved.fragments.Favs;
import com.example.beloved.fragments.Home;
import com.example.beloved.fragments.Profile;
import com.example.beloved.models.ItemViewModel;
import com.example.beloved.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;



public class LandingPage extends AppCompatActivity {
    private ItemViewModel viewModel;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    BottomNavigationView navigationView;
    ArrayList<Post> selectedProds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);
        setupNavigation();
        selectedProds = new ArrayList();

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            selectedProds.add(item);
        });

    }

    //Initialize the bottom navigation view
        //create bottom navigation view object
        private void setupNavigation() {
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        }
}



