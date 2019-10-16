package com.example.sampurnasewaagile;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import Fragments.AddJobFrag;
import Fragments.AdminPage;
import Fragments.HomeFragment;
import Fragments.ProfileFragment;
import Fragments.ViewBookFrag;
import Fragments.ViewBookingFragment;
import Fragments.ViewCompletedFrag;
import Fragments.ViewConfBookFrag;
import Fragments.ViewFeedbackFrag;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        BottomNavigationView botomNav=findViewById(R.id.bottom_nav);
        botomNav.setOnNavigationItemSelectedListener(navList);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont,
                new AddJobFrag()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navList = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_newbook:
                            selectedFragment=new AddJobFrag();
                            break;
                        case R.id.nav_view:
                            selectedFragment=new ViewBookFrag();
                            break;
                        case R.id.nav_viewconf:
                            selectedFragment=new ViewConfBookFrag();
                            break;
                        case R.id.nav_viewcompleted:
                            selectedFragment=new ViewCompletedFrag();
                            break;
                        case R.id.nav_feedback:
                            selectedFragment=new ViewFeedbackFrag();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cont,
                            selectedFragment).commit();

                    return true;
                }
            };
}
