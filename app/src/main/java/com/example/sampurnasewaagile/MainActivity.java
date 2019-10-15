package com.example.sampurnasewaagile;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import Fragments.HomeFragment;
import Fragments.ProfileFragment;
import Fragments.ViewBookingFragment;
import Fragments.ViewUConfBookingFragment;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView botomNav=findViewById(R.id.bottom_navigation);
        botomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment=new HomeFragment();
                            break;
                        case R.id.nav_book:
                            selectedFragment=new ViewBookingFragment();
                            break;
                        case R.id.nav_confbook:
                            selectedFragment=new ViewUConfBookingFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment=new ProfileFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
