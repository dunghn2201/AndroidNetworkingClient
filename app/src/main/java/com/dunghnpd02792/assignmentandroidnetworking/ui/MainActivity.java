package com.dunghnpd02792.assignmentandroidnetworking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.dunghnpd02792.assignmentandroidnetworking.R;
import com.dunghnpd02792.assignmentandroidnetworking.fragment.FavoriteFragment;
import com.dunghnpd02792.assignmentandroidnetworking.fragment.HomeFragment;
import com.dunghnpd02792.assignmentandroidnetworking.fragment.OrderFragment;
import com.dunghnpd02792.assignmentandroidnetworking.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("", ""));
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(HomeFragment.newInstance("", ""));
                            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).
                                    show();
                            return true;
                        case R.id.navigation_order:
                            openFragment(OrderFragment.newInstance("", ""));
                            Toast.makeText(MainActivity.this, "order", Toast.LENGTH_SHORT).
                                    show();
                            return true;
                        case R.id.navigation_favorite:
                            openFragment(FavoriteFragment.newInstance("", ""));
                            Toast.makeText(MainActivity.this, "favorite", Toast.LENGTH_SHORT).
                                    show();
                            return true;
                        case R.id.navigation_profile:
                            openFragment(ProfileFragment.newInstance("", ""));


                            return true;
                    }
                    return false;
                }
            };

}